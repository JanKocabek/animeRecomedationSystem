package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A service for generating anime recommendations based on user preferences and interactions.
 * This service leverages user rating data, user-anime associations, and recommendation algorithms
 * to provide personalized anime suggestions.
 * The service interacts with multiple components to retrieve and process data:
 * - {@link AnimeService} for anime-related operations such as retrieving anime details and IDs.
 * - {@link UserAnimeScoreService} for fetching user-anime relationship data and ratings.
 * - {@link RecommendationEngine} to execute the recommendation algorithm and derive anime suggestions.
 */
@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    AnimeService animeService;
    UserAnimeScoreService userAnimeScoreService;
    RecommendationEngine engine;
    DTOResultBuilder resultBuilder;

    public RecommendationService(AnimeService animeService, RecommendationEngine engine, UserAnimeScoreService userAnimeScoreService, DTOResultBuilder resultBuilder) {
        this.animeService = animeService;
        this.engine = engine;
        this.userAnimeScoreService = userAnimeScoreService;
        this.resultBuilder = resultBuilder;
    }

    /* public endpoints*/
    public RecommendationDTO getAnimeRecommendation(String name) {
        final var animeId = animeService.getAnimeIdByName(name); //one anime id
        return generateAnimeRecommendations(animeId);
    }

    public RecommendationDTO getAnimeRecommendation(Long animeId) {
        return generateAnimeRecommendations(animeId);
    }
    /*---*/

    /**
     * Generates anime recommendations for a given anime ID using an intersection weight algorithm
     * along with various processing and analysis steps to refine the recommendations.
     *
     * @param animeId the ID of the anime for which recommendations are generated
     * @return a {@link RecommendationDTO} object containing the input anime names
     *         and a list of recommended anime
     */
    private RecommendationDTO generateAnimeRecommendations(Long animeId) {
        //collecting and grouping data from the database into a list of users Anime lists
        final var userAnimeData = collectUsersAnimeLists(animeId);
        logger.info("size of data after grouping: {}", userAnimeData.size());

        final var sortedMap = engine.buildAnimeOccurrencesMap(userAnimeData);
        logger.debug("number of anime in map: {}", sortedMap.size());


        final var weightedAnime = engine.weightAnime(sortedMap, AnimeScore.compositeScoring);
        final var topRecommendations = engine.cutTheTopN(weightedAnime);//current final list with id without detail yet
        logger.debug("size of shortened list: {}", topRecommendations.size());
        final var animeDetailsList = animeService.getListAnimeFromIds(getAnimeIDsFromDTO(topRecommendations));
        logger.debug("recommended anime: {}", animeDetailsList.size());
        return buildOutputList(animeDetailsList, topRecommendations);
    }

    private List<UserAnimeList> collectUsersAnimeLists(Long animeId) {
        final var usersId = userAnimeScoreService.getUserWithAnime(animeId);
        logger.info("Users with anime after service: {}", usersId.size());
        final var userRatingsData  = userAnimeScoreService.fetchRatedAnimeByUsers(usersId, animeId, Pageable.unpaged());
        return  engine.groupUserByID(userRatingsData);

    }

    private Collection<Long> getAnimeIDsFromDTO(Map<Long, AnimeOutDTO> animeMap) {
        return animeMap.keySet().stream().toList();
    }

    private RecommendationDTO buildOutputList(List<AnimeDto> animeDetails, Map<Long, AnimeOutDTO> outDTO) {
        for (AnimeDto detail : animeDetails) {
            outDTO.get(detail.id()).setAnimeInfo(detail);
        }
        return resultBuilder.addRecommendation(convertToAnimeList(outDTO)).build();
    }

    private List<AnimeOutDTO> convertToAnimeList(Map<Long, AnimeOutDTO> animeData) {
        animeData.forEach((key, value) -> value.setAnimeId(key));
        return animeData.values().stream().toList();
    }
}

