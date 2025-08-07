package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.service.RecommendationConfig.RecommendationConfig;

import java.util.function.ToDoubleFunction;

public class AnimeScore {

    public static ToDoubleFunction<AnimeOutDTO> compositeScoring = (anime) -> {
        double normalizedOccurrences = anime.getPercentageOccurrences() / 100;
        double normalizedRating = anime.getAverageRating() / 10;
        return (normalizedOccurrences * RecommendationConfig.OCCURRENCE_WEIGHT) + (normalizedRating * RecommendationConfig.SCORE_WEIGHT);
    };


}

