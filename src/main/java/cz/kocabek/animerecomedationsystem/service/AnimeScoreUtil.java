package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class AnimeScoreUtil {
    // take only anime with rating x to y
    static Map<Long, Integer> cutTheTopNByRanking(Map<Long, Integer> map, int minRanking, int maxRanking) {
        final var result = new LinkedHashMap<Long, Integer>();
        for (var e : map.entrySet()) {
            if (e.getValue() >= minRanking && e.getValue() <= maxRanking) {
                result.putIfAbsent(e.getKey(), e.getValue());
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Sorts the anime map of the given user's anime list in descending order based on their rankings.
     * Returns the sorted map.
     *
     * @param user the {@link UserAnimeList} record containing a map of anime titles with their respective rankings
     * @return a sorted {@link Map} with anime titles as keys and their rankings as values, ordered in descending order of rankings
     */
    static Map<Long, Integer> sortAnimeMapByRanking(UserAnimeList user) {
        return user.animeList().entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }
}
