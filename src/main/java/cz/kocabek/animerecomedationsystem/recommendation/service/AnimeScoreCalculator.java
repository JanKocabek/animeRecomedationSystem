package cz.kocabek.animerecomedationsystem.recommendation.service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.ConfigConstant;

import java.util.function.ToDoubleFunction;

 class AnimeScoreCalculator {

    static ToDoubleFunction<AnimeOutDTO> compositeScoring = (anime) -> {
        final double normalizedOccurrences = anime.getPercentageOccurrences() / 100;
        final double normalizedRating = anime.getAverageRating() / 10;
        return (normalizedOccurrences * ConfigConstant.OCCURRENCE_WEIGHT) + (normalizedRating * ConfigConstant.SCORE_WEIGHT);
    };
}

