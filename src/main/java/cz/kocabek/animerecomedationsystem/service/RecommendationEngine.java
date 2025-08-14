package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.service.RecommendationConfig.ConfigConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



    /** counting anime occurrences in the given users anime lists
     * automatically transfers dta from list to map
     *
     * @param userAnimeLists @List<{@link UserAnimeList>} users anime lists with anime IDs and their respective ratings
     *
     *
     *  @return @Map<{@link Long}, {@link Integer}> anime ID and its occurrence across the Users
     *
     */
    private Map<Long, AnimeOutDTO> countAnimeOccurrences(List<UserAnimeList> userAnimeLists) {
        logger.debug("number of users: {}", userAnimeLists.size());
        final Map<Long, AnimeOutDTO> result = new LinkedHashMap<>();
        for (UserAnimeList user : userAnimeLists) {
            user.animeList().forEach((animeId, rating) -> {
                if (!result.containsKey(animeId)) {
                    AnimeOutDTO anime = new AnimeOutDTO(rating, 1);
                    result.put(animeId, anime);
                } else {
                    result.get(animeId).addToRatingSum(rating);
                    result.get(animeId).incrementOccurrences();
                }
            });
        }
        return result;
    }

    private void calculateAverageRatings(Map<Long, AnimeOutDTO> animeData) {
        animeData.forEach((_, anime) -> anime.calculateAverageRating());
    }

    private void calculatePercentageOccurrencesAmongUsers(Map<Long, AnimeOutDTO> animeData, int usersCount) {
        animeData.forEach((_, anime) ->
                anime.setPercentageOccurrences((anime.getOccurrences() / (double) usersCount) * 100));
    }

    private Map<Long, AnimeOutDTO> sortAnimeMapByOccurrences(Map<Long, AnimeOutDTO> animeData) {
        return animeData.entrySet().stream().sorted(
                        (e1, e2) -> e2.getValue().getOccurrences() - e1.getValue().getOccurrences())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }

    Map<Long, AnimeOutDTO> weightAnime(Map<Long, AnimeOutDTO> animeList, ToDoubleFunction<AnimeOutDTO> weightFunction) {
        return animeList.entrySet().stream()
                .sorted(Comparator.<Map.Entry<Long, AnimeOutDTO>>comparingDouble(
                        e -> weightFunction.applyAsDouble(e.getValue())).reversed()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }

    Map<Long, AnimeOutDTO> cutTheTopN(Map<Long, AnimeOutDTO> animeList) {
        return animeList.entrySet().stream()
                .limit(Math.min(animeList.size(), ConfigConstant.FINAL_ANIME_LIST_SIZE))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }

    /**
     * Processes a list of user anime ratings to build a sorted map of anime occurrences with calculated statistics.
     * This method performs the following operations:
     * 1. Counts how many times each anime appears across all user lists
     * 2. Calculates the average rating for each anime
     * 3. Calculates the percentage of occurrence for each anime
     * 4. Sorts the anime by their occurrence count in descending order
     *
     * @param animeLists a list of UserAnimeList objects containing user IDs and their anime ratings
     * @return a sorted Map where keys are anime IDs and values are AnimeOutDTO objects containing 
     *         occurrence statistics and rating information
     */
    Map<Long, AnimeOutDTO> buildAnimeOccurrencesMap(List<UserAnimeList> animeLists) {
        final var map = countAnimeOccurrences(animeLists);
        logger.debug("size of all anime: {}", map.size());
        calculateAverageRatings(map);
        calculatePercentageOccurrencesAmongUsers(map, animeLists.size());

        return sortAnimeMapByOccurrences(map);
    }
}
