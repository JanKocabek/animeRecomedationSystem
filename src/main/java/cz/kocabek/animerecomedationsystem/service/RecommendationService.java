package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    AnimeService animeService;
    UserAnimeScoreService userAnimeScoreService;
    UsersAnimeScoreRepository usersAnimeScoreRepository;

    public RecommendationService(AnimeService animeService , UsersAnimeScoreRepository usersAnimeScoreRepository, UserAnimeScoreService userAnimeScoreService) {
        this.animeService = animeService;
        this.userAnimeScoreService = userAnimeScoreService;
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
    }

    public Map<String, Integer> getAnimeRecommendation(String name) {
        Long animeId = animeService.getAnimeIdByName(name);
        List<Long> usersId = userAnimeScoreService.getUserWithAnime(animeId);
        logger.info("Users with anime after service: {}", usersId.size());
        final var data = groupedUsersLists(usersId, animeId);
        logger.info("size of data after grouping: {}", data.size());
        final var highRankedData = sectionByRank(5, 10, data);
        logger.debug("size of high ranked data: {}", highRankedData.size());
        return findIntersectedAnime(highRankedData);
    }

    /**
     * Groups a list of user IDs by their rated anime, creating a list of UserAnimeList records.
     *
     * @param usersId a list of user IDs whose rated anime and scores should be retrieved and grouped
     * @return a list of UserAnimeList records, where each record contains a user ID and a map of anime titles with their respective ratings
     */
    private List<UserAnimeList> groupedUsersLists(List<Long> usersId, Long animeId) {
        final var data = usersAnimeScoreRepository.getUsersListRatedAnime(usersId, animeId, PageRequest.of(0, 20000));
        logger.debug("size of fetch data:  {}",data.getContent().size());
        // grouping records based UserID Map<Long, List<UsersAnimeScoreDto>>
        // making name and rating from list -> Map.Entry<Long, Map<String, Integer>>
        List<UserAnimeList> list = data.get()
                .collect(Collectors.groupingBy(UsersAnimeScoreDto::userId)) // grouping records based UserID Map<Long, List<UsersAnimeScoreDto>>
                .entrySet().stream().
                map(e -> {
                    Map<String, Integer> map = e.getValue().stream().collect(Collectors.toMap(UsersAnimeScoreDto::animeName, UsersAnimeScoreDto::rating, (_, b) -> b));
                    return Map.entry(e.getKey(), map);
                    // making  name and rating from list -> Map.Entry<Long, Map<String, Integer>>
                })
                .map(entry -> new UserAnimeList(entry.getKey(), entry.getValue()))
                .toList();
        return list;
    }


    /**
     *filter anime in given rating ranks and create copy of
     *
     * @param minRank minimal anime rank
     * @param maxRank maximal anime rank
     * @param data @List<{@link UserAnimeList>} data which want to section
     * @return dissected data
     */
    private List<UserAnimeList> sectionByRank(int minRank, int maxRank, List<UserAnimeList> data) {
        logger.debug("data size is correct: {}", data.size());
        final var rankedSection = new ArrayList<UserAnimeList>();
        data.forEach(
                user -> rankedSection.add(new UserAnimeList(user.id(), cutTheTopNByRanking(sortAnimeMapByRanking(user), minRank, maxRank)))
        );
        logger.debug("ranked section size: {}", rankedSection.size());
        return rankedSection;
    }

    private Map<String, Integer> findIntersectedAnime(List<UserAnimeList> data) {
        logger.debug("data size: {}", data.size());
        //  data.forEach(user -> logger.debug("user: {}", user.id()));
        final var initialList = new HashMap<>(data.getFirst().animeList());//copy of data to not mess with
        var lastIntersectList = new HashMap<>(initialList);//for getting results earlier if the intersection goes to 0
        if (data.size() > 1) {
            for (UserAnimeList user : data.subList(1, data.size())) {
                boolean _ = initialList.keySet().retainAll(user.animeList().keySet());
                if (initialList.isEmpty()) {
                    logger.debug("initial list is empty");
                    break;
                }
                lastIntersectList = new HashMap<>(initialList);
                //logger.debug("intersected list number: {}", initialList.keySet());
            }
        }
        return lastIntersectList;
    }

    /**
     * Sorts the anime map of the given user's anime list in descending order based on their rankings.
     * Updates the user's anime list with the sorted map and returns the sorted map.
     *
     * @param user the {@link UserAnimeList} record containing a map of anime titles with their respective rankings
     * @return a sorted {@link Map} with anime titles as keys and their rankings as values, ordered in descending order of rankings
     */
    private Map<String, Integer> sortAnimeMapByRanking(UserAnimeList user) {
        Map<String, Integer> rankedUserAnimeMap =
                user.animeList().entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue, (old, _) -> old, LinkedHashMap::new));
        user.animeList().clear();
        user.animeList().putAll(rankedUserAnimeMap);
        return rankedUserAnimeMap;
    }


    private Map<String, Integer> cutTheTopNByRanking(Map<String, Integer> map, int minRanking, int maxRanking) {
        final var result = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (e.getValue() >= minRanking && e.getValue() <= maxRanking) {
                result.putIfAbsent(e.getKey(), e.getValue());
            } else {
                break;
            }
        }
        return result;
    }

    // private

}

