package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeDto;
import cz.kocabek.animerecomedationsystem.service.config.SystemConfConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return recommendAnimeBasedOnUsersIntersection(animeId);
    }

    public List<AnimeDto> getAnimeRecommendation(Long animeId) {
        return recommendAnimeBasedOnUsersIntersection(animeId);
    }
    /*---*/

    //main process method for the output - currently intersection algorithm
    private List<AnimeDto> recommendAnimeBasedOnUsersIntersection(Long animeId) {
        final var usersId = userAnimeScoreService.getUserWithAnime(animeId);
        logger.info("Users with anime after service: {}", usersId.size());
        final var userRatingsData = userAnimeScoreService.fetchRatedAnimeByUsers(usersId, animeId, Pageable.unpaged());
        final var userAnimeLists = recommendationEngine.groupUserByID(userRatingsData);
        logger.info("size of data after grouping: {}", userAnimeLists.size());
        final var highRankedData = recommendationEngine.filterUserListsByRank(SystemConfConst.MIN_RATING_GET, userAnimeLists);
        logger.debug("size of high ranked data: {}", highRankedData.size());
        final var animeOcurrencesMap = recommendationEngine.countAnimeOccurrences(highRankedData);
        logger.debug("size of intersected anime: {}", animeOcurrencesMap.size());
        return animeService.getListAnimeFromIds(animeOcurrencesMap.keySet());
    }




}

