package cz.kocabek.animerecomedationsystem.recommendation.service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeDto;
import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.recommendation.dto.RecommendationDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A service for generating detail recommendations based on user preferences and interactions.
 * This service leverages user rating data, user-detail associations, and recommendation algorithms
 * to provide personalized detail suggestions.
 * The service interacts with multiple components to retrieve and process data:
 * - {@link AnimeService} for detail-related operations such as retrieving detail details and IDs.
 * - {@link UserAnimeScoreService} for fetching user-detail relationship data and ratings.
 * - {@link RecommendationEngine} to execute the recommendation algorithm and derive detail suggestions.
 */
@AllArgsConstructor
@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    AnimeService animeService;
    UserAnimeScoreService userAnimeScoreService;
    RecommendationEngine engine;
    DTOResultBuilder resultBuilder;
    AnimeGenreService animeGenreService;


    /* public endpoints*/
    public RecommendationDTO getAnimeRecommendation(String name) {
        final var animeId = animeService.getAnimeIdByName(name); //one detail id
        return generateAnimeRecommendations(animeId);
    }

    public RecommendationDTO getAnimeRecommendation(Long animeId) {
        return generateAnimeRecommendations(animeId);
    }

    /*---*/


    /**
     * Generates detail recommendations for a given detail ID using an intersection weight algorithm
     * along with various processing and analysis steps to refine the recommendations.
     *
     * @param animeId the ID of the detail for which recommendations are generated
     * @return a {@link RecommendationDTO} object containing the input detail names
     *         and a list of recommended detail
     */
    private RecommendationDTO generateAnimeRecommendations(Long animeId) {
        //collecting and grouping data from the database into a list of users Anime lists
        long step1Start = System.nanoTime();
        final var usersAnimeLists = userAnimeScoreService.getUsersAnimeLists(animeId);
        long step1Duration = (System.nanoTime() - step1Start) / 1_000_000;
        logger.warn("Step 1 (collect users data) took: {} ms", step1Duration);
        logger.info("size of data after grouping: {}", usersAnimeLists.size());

        long step2Start = System.nanoTime();
        final var sortedMap = engine.buildAnimeOccurrencesMap(usersAnimeLists);
        long step2Duration = (System.nanoTime() - step2Start) / 1_000_000;
        logger.warn("Step 2 (build detail occurrences map) took: {} ms", step2Duration);
        logger.debug("number of detail in map: {}", sortedMap.size());

        long step3Start = System.nanoTime();
        final var weightedAnime = engine.weightAnime(sortedMap, AnimeScore.compositeScoring);
        final var topRecommendations = engine.cutTheTopN(weightedAnime);//current final map with ids without detail yet
        long step3Duration = (System.nanoTime() - step3Start) / 1_000_000;
        logger.warn("Step 3 (weight detail) took: {} ms", step3Duration);
        logger.debug("size of shortened list: {}", topRecommendations.size());
        long step4Start = System.nanoTime();
        final var animeDetailsList = getAnimeDetails(topRecommendations);

        long step4Duration = (System.nanoTime() - step4Start) / 1_000_000;
        logger.warn("Step 4 (get detail details) took: {} ms", step4Duration);
        logger.debug("recommended detail: {}", animeDetailsList.size());
        return buildOutputList(animeDetailsList, topRecommendations);
    }


    private Collection<Long> getAnimeIDsFromDTO(Map<Long, AnimeOutDTO> animeMap) {
        return animeMap.keySet().stream().toList();
    }

    private RecommendationDTO buildOutputList(List<AnimeDto> animeDetails, Map<Long, AnimeOutDTO> outDTO) {
        for (AnimeDto detail : animeDetails) {
            outDTO.get(detail.getId()).setAnimeInfo(detail);
        }
        return resultBuilder.addRecommendation(convertToAnimeList(outDTO)).build();
    }

    private List<AnimeOutDTO> convertToAnimeList(Map<Long, AnimeOutDTO> animeData) {
        animeData.forEach((key, value) -> value.setAnimeId(key));
        return animeData.values().stream().toList();
    }

    private List<AnimeDto> getAnimeDetails(Map<Long, AnimeOutDTO> topRecommendations) {
        final var details = animeService.getListAnimeFromIds(getAnimeIDsFromDTO(topRecommendations));
        final var genresDetail = animeGenreService.getGenreForAnime(topRecommendations.keySet());
        for (AnimeDto detail : details) {
            detail.getGenres().addAll(genresDetail.get(detail.getId()));
        }
        return details;
    }
}

