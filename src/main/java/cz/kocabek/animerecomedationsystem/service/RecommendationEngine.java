package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.service.RecommendationConfig.RecommendationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * A service responsible for implementing the recommendation engine logic.
 * This service aggregates user anime ratings and analyzes patterns to aid in recommendation generation.
 */
@Service
public class RecommendationEngine {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationEngine.class);

    public RecommendationEngine() {
    }

    /**
     * Groups the provided data of anime scores by user ID and constructs a list of {@link UserAnimeList}
     * objects containing user IDs and their respective anime ratings.
     *
     * @param data a {@link Slice} of {@link UsersAnimeScoreDto} objects representing user ratings for various anime
     * @return a {@link List} of {@link UserAnimeList} containing user IDs and their mapped anime scores
     */
    List<UserAnimeList> groupUserByID(Slice<UsersAnimeScoreDto> data) {
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

    /** counting anime occurrences in the given data
     * @param userAnimeData @List<{@link UserAnimeList>} users anime lists with anime IDs and their respective ratings
     *
     *
     *  @return @Map<{@link Long}, {@link Integer}> anime ID and its occurrence across the Users
     *
     */
    Map<Long, AnimeOutDTO> countAnimeOccurrences(List<UserAnimeList> userAnimeData) {
        logger.debug("number of users: {}", userAnimeData.size());
        final Map<Long, AnimeOutDTO> result = new LinkedHashMap<>();
        for (UserAnimeList user : userAnimeData) {
            user.animeList().forEach((animeId, rating) -> {
                if (!result.containsKey(animeId)) {
                    AnimeOutDTO anime = new AnimeOutDTO(rating, 1);
                    result.put(animeId, anime);
                } else {
                    result.get(animeId).adToRatings(rating);
                    result.get(animeId).addToOccurrences();
                }
            });
        }
        return result;
    }

    void calculateAverageRatings(Map<Long, AnimeOutDTO> animeData) {
        animeData.forEach((_, anime) -> anime.calculateAverageRating());
    }

    void calculatePercentageOccurrences(Map<Long, AnimeOutDTO> animeData) {
        animeData.forEach((_, anime) -> anime.setPercentageOccurrences(anime.getOccurrences() / (double) animeData.size()));
    }

    Map<Long, AnimeOutDTO> sortAnimeMapByOccurrences(Map<Long, AnimeOutDTO> animeData) {
        return animeData.entrySet().stream().sorted(
                        (e1, e2) -> e2.getValue().getOccurrences() - e1.getValue().getOccurrences())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }

    Map<Long, AnimeOutDTO> weightAnime(Map<Long, AnimeOutDTO> animeList, ToDoubleFunction<AnimeOutDTO> weightFunction) {
        return animeList.entrySet().stream().sorted(Comparator.comparingDouble(
                        e -> weightFunction.applyAsDouble(e.getValue())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }

    Map<Long, AnimeOutDTO> cutTheTopN(Map<Long, AnimeOutDTO> animeList) {
        return animeList.entrySet().stream()
                .limit(Math.min(animeList.size(), RecommendationConfig.FINAL_ANIME_LIST_SIZE))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }
}
