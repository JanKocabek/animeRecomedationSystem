package cz.kocabek.animerecomedationsystem.recommendation.dto;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Genre;

/**
 * Projection for {@link Genre}
 */
public interface GenreInfo {
    String getGenreName();
}