package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import cz.kocabek.animerecomedationsystem.service.config.SystemConfConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationEngine {

    Logger logger = LoggerFactory.getLogger(RecommendationEngine.class);

    AnimeService animeService;
    UserAnimeScoreService userAnimeScoreService;
    UsersAnimeScoreRepository usersAnimeScoreRepository;

    public RecommendationEngine(AnimeService animeService, UsersAnimeScoreRepository usersAnimeScoreRepository, UserAnimeScoreService userAnimeScoreService) {
        this.animeService = animeService;
        this.userAnimeScoreService = userAnimeScoreService;
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
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
        final var list = data.get()
                .collect(Collectors.groupingBy(UsersAnimeScoreDto::userId)) // grouping records based UserID Map<Long, List<UsersAnimeScoreDto>>
                .entrySet().stream().
                map(e -> {
                    var map = e.getValue().stream().collect(Collectors.toMap(UsersAnimeScoreDto::animeId, UsersAnimeScoreDto::rating, (_, b) -> b));
                    return Map.entry(e.getKey(), map);
                    // making name and rating from list -> Map.Entry<Long, Map<String, Integer>>
                })
                .map(entry -> new UserAnimeList(entry.getKey(), entry.getValue()))
                .toList();
        return list;
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
//       if(logger.isDebugEnabled()){
//           for (int i = 0; i < 6; i++) {
//               UserAnimeList user = data.get(i);
//               logger.debug("user: {}", user.id());
//               logger.debug("anime list: {}", user.animeList().size());
//           }
//           logger.debug("_____");
//       }
        final var rankedSection = new ArrayList<UserAnimeList>();
        data.forEach(
                user -> rankedSection.add(new UserAnimeList(user.id(), cutTheTopNByRanking(sortAnimeMapByRanking(user), minRank, maxRank)))
        );
//        if(logger.isDebugEnabled()){
//            logger.debug("after section");
//            for (int i = 0; i <6; i++) {
//                UserAnimeList user = rankedSection.get(i);
//                logger.debug("user: {}", user.id());
//                logger.debug("anime list: {}", user.animeList().size());
//            }
//            logger.debug("ranked section size: {}", rankedSection.size());
//        }
        return rankedSection;
    }

     List<UserAnimeList> filterUserListsByRank(int minRank, List<UserAnimeList> data) {
        return filterUserListsByRank(minRank, SystemConfConst.MAX_SCORE, data);
    }

    //comparing lists which anime is common across all users
//     Map<Long, Integer> findIntersectedAnime(List<UserAnimeList> data) {
//        logger.debug("data size: {}", data.size());
//        //  data.forEach(user -> logger.debug("user: {}", user.id()));
//        final var initialList = new HashMap<>(data.getFirst().animeList());//copy of data to doesn't mess with the original data
//        var lastIntersectList = new HashMap<>(initialList);//for getting results earlier if the intersection goes to 0
//        if (data.size() > 1) {
//            for (UserAnimeList user : data.subList(1, data.size())) {
//                boolean _ = initialList.keySet().retainAll(user.animeList().keySet());
//                if (initialList.isEmpty()) {
//                    logger.debug("initial list is empty");
//                    break;
//                }
//                lastIntersectList = new HashMap<>(initialList);
//                //logger.debug("intersected list number: {}", initialList.keySet());
//            }
//        }
//        return lastIntersectList;
//    }

    Map<Long,Integer> countAnimeOccurrences(List<UserAnimeList> data) {
         logger.debug("data size: {}", data.size());
         final Map<Long,Integer> result = new LinkedHashMap<>();
        for (UserAnimeList user : data) {
            user.animeList().forEach((key, _) -> result.put(key,
                    !result.containsKey(key) ? 1 : result.get(key) + 1));
        }
        return result;
    }

    /**
     * Sorts the anime map of the given user's anime list in descending order based on their rankings.
     * Updates the user's anime list with the sorted map and returns the sorted map.
     *
     * @param user the {@link UserAnimeList} record containing a map of anime titles with their respective rankings
     * @return a sorted {@link Map} with anime titles as keys and their rankings as values, ordered in descending order of rankings
     */
    Map<Long, Integer> sortAnimeMapByRanking(UserAnimeList user) {
        final var rankedUserAnimeMap =
                user.animeList().entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
        user.animeList().clear();
        user.animeList().putAll(rankedUserAnimeMap);
        return rankedUserAnimeMap;
    }

    // take only anime with rating x to y
    Map<Long, Integer> cutTheTopNByRanking(Map<Long, Integer> map, int minRanking, int maxRanking) {
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

}
