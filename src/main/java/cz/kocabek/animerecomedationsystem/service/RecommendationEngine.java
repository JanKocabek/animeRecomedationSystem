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

import static cz.kocabek.animerecomedationsystem.service.AnimeScoreUtil.sortAnimeMapByRanking;

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
                user -> rankedSection.add(new UserAnimeList(user.id(), AnimeScoreUtil.cutTheTopNByRanking(sortAnimeMapByRanking(user), minRank, maxRank)))
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

    Map<Long,Integer> countAnimeOccurrences(List<UserAnimeList> data) {
         logger.debug("data size: {}", data.size());
         final Map<Long,Integer> result = new LinkedHashMap<>();
        for (UserAnimeList user : data) {
            user.animeList().forEach((key, _) -> result.put(key,
                    !result.containsKey(key) ? 1 : result.get(key) + 1));
        }
        return result;
    }




}
