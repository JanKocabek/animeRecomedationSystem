package cz.kocabek.animerecomedationsystem.recommendation.service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.ConfigConstant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimeScoreTest {

    @Test
    void compositeScoring() {
        //arrange
        AnimeOutDTO anime = new AnimeOutDTO(80, 10);
        anime.calculateAverageRating();
        anime.setPercentageOccurrences(25);
        var normOccurrences = anime.getPercentageOccurrences() / 100;
        var normRating = anime.getAverageRating() / 10;
        var expected = normOccurrences * ConfigConstant.OCCURRENCE_WEIGHT + normRating * ConfigConstant.SCORE_WEIGHT;
        //act
        var result = AnimeScoreCalculator.compositeScoring.applyAsDouble(anime);
        //assert
        assertEquals(expected, result);
    }

    @Test
    void perfectScore() {
        //arrange
        final var anime = new AnimeOutDTO(100, 10);
        anime.calculateAverageRating();
        anime.setPercentageOccurrences(100);
        //act
        var result = AnimeScoreCalculator.compositeScoring.applyAsDouble(anime);
        //assert
        assertThat(result).isEqualTo(1);
    }
}