package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service for generating anime recommendations based on user preferences and interactions.
 * This service leverages user rating data, user-anime associations, and recommendation algorithms
 * to provide personalized anime suggestions.
 *
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
    RecommendationEngine recommendationEngine;


    public RecommendationService(AnimeService animeService, RecommendationEngine recommendationEngine, UserAnimeScoreService userAnimeScoreService) {
        this.animeService = animeService;
        this.recommendationEngine = recommendationEngine;
        this.userAnimeScoreService = userAnimeScoreService;
    }

    /* public endpoints*/
    public List<AnimeDto> getAnimeRecommendation(String name) {
        final var animeId = animeService.getAnimeIdByName(name); //one anime id
        return generateAnimeRecommendations(animeId);
    }

    public List<AnimeDto> getAnimeRecommendation(Long animeId) {
        return generateAnimeRecommendations(animeId);
    }
    /*---*/

    //main process method for the output - currently intersection algorithm
    private List<AnimeDto> generateAnimeRecommendations(Long animeId) {
        final var usersId = userAnimeScoreService.getUserWithAnime(animeId);
        logger.info("Users with anime after service: {}", usersId.size());
        final var userRatingsData = userAnimeScoreService.fetchRatedAnimeByUsers(usersId, animeId, Pageable.unpaged());
        final var userAnimeLists = recommendationEngine.groupUserByID(userRatingsData);
        logger.info("size of data after grouping: {}", userAnimeLists.size());
        final var animeOcurrencesMap = recommendationEngine.countAnimeOccurrences(userAnimeLists);
        logger.debug("size of intersected anime: {}", animeOcurrencesMap.size());
        return animeService.getListAnimeFromIds(animeOcurrencesMap.keySet());
    }
}

