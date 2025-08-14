package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.service.RecommendationConfig.ConfigConstant;

import java.util.function.ToDoubleFunction;

class AnimeScore {

    static ToDoubleFunction<AnimeOutDTO> compositeScoring = (anime) -> {
        final double normalizedOccurrences = anime.getPercentageOccurrences() / 100;
        final double normalizedRating = anime.getAverageRating() / 10;
        return (normalizedOccurrences * ConfigConstant.OCCURRENCE_WEIGHT) + (normalizedRating * ConfigConstant.SCORE_WEIGHT);
    };
}

