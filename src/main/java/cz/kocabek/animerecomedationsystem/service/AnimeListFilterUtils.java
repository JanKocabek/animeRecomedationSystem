package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.service.RecommendationConfig.RecommendationConfig;

import java.util.*;
import java.util.stream.Collectors;


public class AnimeListFilterUtils {
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

    /**
     *filter anime in given rating ranks and create a copy of it
     *
     * @param minRank minimal anime rank
     * @param maxRank maximal anime rank
     * @param data @List<{@link UserAnimeList>} data which want to section
     * @return dissected data
     */
    List<UserAnimeList> filterUserListsByRank(int minRank, int maxRank, List<UserAnimeList> data) {
        final var rankedSection = new ArrayList<UserAnimeList>();
        data.forEach(
                user -> rankedSection.add(new UserAnimeList(user.id(), AnimeListFilterUtils.cutTheTopNByRanking(sortAnimeMapByRanking(user), minRank, maxRank)))
        );
        return rankedSection;
    }

    List<UserAnimeList> filterUserListsByRank(int minRank, List<UserAnimeList> data) {
        return filterUserListsByRank(minRank, RecommendationConfig.MAX_SCORE, data);
    }

}
