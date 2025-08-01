package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    AnimeService animeService;
    UserServices userServices;
    UsersAnimeScoreRepository usersAnimeScoreRepository;

    public RecommendService(AnimeService animeService, UserServices userServices, UsersAnimeScoreRepository usersAnimeScoreRepository) {
        this.animeService = animeService;
        this.userServices = userServices;
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
    }

    public List<UserAnimeList> getAnimeRecommendation(String name) {
        Long id = animeService.getAnimeIdByName(name);
        List<Long> usersId = userServices.getUserWithAnime(id);
        return groupedUsersLists(usersId);
    }

    /**
     * Groups a list of user IDs by their rated anime, creating a list of UserAnimeList records.
     *
     * @param usersId a list of user IDs whose rated anime and scores should be retrieved and grouped
     * @return a list of UserAnimeList records, where each record contains a user ID and a map of anime titles with their respective ratings
     */
    private List<UserAnimeList> groupedUsersLists(List<Long> usersId) {
        final var data = usersAnimeScoreRepository.getUsersListRatedAnime(usersId, PageRequest.of(0, 300));
        return data.get()
                .collect(Collectors.groupingBy(UsersAnimeScoreDto::userId)) // grouping records based UserID Map<Long, List<UsersAnimeScoreDto>>
                .entrySet().stream()
                .map(e -> {
                    Map<String, Integer> map = e.getValue().stream().collect(Collectors.toMap(UsersAnimeScoreDto::animeName, UsersAnimeScoreDto::rating, (_, b) -> b));
                    return Map.entry(e.getKey(), map); // amking  name and rating from list -> Map.Entry<Long, Map<String, Integer>>
                })
                .map(entry -> new UserAnimeList(entry.getKey(), entry.getValue()))
                .toList();
    }

}

