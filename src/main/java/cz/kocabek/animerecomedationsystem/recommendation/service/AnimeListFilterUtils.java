package cz.kocabek.animerecomedationsystem.recommendation.service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.ConfigConstant;

import java.util.*;
import java.util.stream.Collectors;


public class AnimeListFilterUtils {
    // take only detail with rating x to y
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
     * Sorts the detail map of the given user's detail list in descending order based on their rankings.
     * Returns the sorted map.
     *
     * @param user the {@link UserAnimeList} record containing a map of detail titles with their respective rankings
     * @return a sorted {@link Map} with detail titles as keys and their rankings as values, ordered in descending order of rankings
     */
    static Map<Long, Integer> sortAnimeMapByRanking(UserAnimeList user) {
        return user.animeList().entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
    }

    /**
     *filter detail in given rating ranks and create a copy of it
     *
     * @param minRank minimal detail rank
     * @param maxRank maximal detail rank
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
        return filterUserListsByRank(minRank, ConfigConstant.MAX_SCORE, data);
    }

}
