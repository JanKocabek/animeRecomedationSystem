package cz.kocabek.animerecomedationsystem.recommendation.service.db;

import cz.kocabek.animerecomedationsystem.recommendation.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.recommendation.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.recommendation.repository.UsersAnimeScoreRepository;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.RecommendationConfig;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.ConfigConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserAnimeScoreService {
    private static final Logger logger = LoggerFactory.getLogger(UserAnimeScoreService.class);
    UsersAnimeScoreRepository usersAnimeScoreRepository;
    RecommendationConfig config;

    public UserAnimeScoreService(UsersAnimeScoreRepository usersAnimeScoreRepository, RecommendationConfig config) {
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
        this.config = config;
    }

    public List<UserAnimeList> getUsersAnimeLists(Long animeId) {
        long step1_1Start = System.nanoTime();
        final var usersId = getUsersIdWhoRatedGivenAnime(animeId);
        long step1_1Duration = (System.nanoTime() - step1_1Start) / 1_000_000;
        logger.warn("Step 1.1 (collect users with detail) took: {} ms", step1_1Duration);
        logger.info("Users with detail after service: {}", usersId.size());

        long step1_2Start = System.nanoTime();
        final var userRatingsData = fetchRatedAnimeByUsers(usersId, animeId, Pageable.unpaged());
        long step1_2Duration = (System.nanoTime() - step1_2Start) / 1_000_000;
        logger.warn("Step 1.2 (fetch user ratings) took: {} ms", step1_2Duration);
        return groupUserByID(userRatingsData);
    }

    private List<Long> getUsersIdWhoRatedGivenAnime(Long aniId) {
        return usersAnimeScoreRepository.findUsersIdByAnimeIdAndRatingRange(aniId, config.getMinScore(),
                ConfigConstant.MAX_INPUT_SCORE,
                PageRequest.of(0, config.getMaxUsers())).getContent();
    }

    //fetching detail ranking records from a given userIdList and detail ID
    private Slice<UsersAnimeScoreDto> fetchRatedAnimeByUsers(List<Long> usersId, Long animeId, Pageable pageable) {
        final var ratedAnimeData = usersAnimeScoreRepository.getUsersListRatedAnime(usersId, animeId, pageable);
        logger.debug("size of fetch data:  {}", ratedAnimeData.getContent().size());
        //logger.debug("size of fetch Animedata: {} vs asked: {}", ratedAnimeData.getNumberOfElements(), limit);
        return ratedAnimeData;
    }

    /**
     * Groups the provided data of detail scores by user ID and constructs a list of {@link UserAnimeList}
     * objects containing user IDs and their respective detail ratings.
     *
     * @param data a {@link Slice} of {@link UsersAnimeScoreDto} objects representing user ratings for various detail
     * @return a {@link List} of {@link UserAnimeList} containing user IDs and their mapped detail scores
     */
    private List<UserAnimeList> groupUserByID(Slice<UsersAnimeScoreDto> data) {
        // grouping records based UserID Map<Long, List<UsersAnimeScoreDto>>
        // making name and rating from list -> Map.Entry<Long, Map<String, Integer>>
        return data.get()
                .collect(Collectors.groupingBy(UsersAnimeScoreDto::userId)) // grouping records based UserID Map<Long, List<UsersAnimeScoreDto>>
                .entrySet().stream().
                map(e -> {
                    final var map = e.getValue().stream().collect(Collectors.toMap(UsersAnimeScoreDto::animeId, UsersAnimeScoreDto::rating, (_, b) -> b));
                    return Map.entry(e.getKey(), map);
                    // making name and rating from list -> Map.Entry<Long, Map<String, Integer>>
                })
                .map(entry -> new UserAnimeList(entry.getKey(), entry.getValue()))
                .toList();
    }
}
