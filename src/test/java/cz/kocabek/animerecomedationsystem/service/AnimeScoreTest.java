package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.service.RecommendationConfig.RecommendationConfig;
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
        var expected = normOccurrences * RecommendationConfig.OCCURRENCE_WEIGHT + normRating * RecommendationConfig.SCORE_WEIGHT;
        //act
        var result = AnimeScore.compositeScoring.applyAsDouble(anime);
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
        var result = AnimeScore.compositeScoring.applyAsDouble(anime);
        //assert
        assertThat(result).isEqualTo(1);
    }
}