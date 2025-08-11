package cz.kocabek.animerecomedationsystem.dto;

import cz.kocabek.animerecomedationsystem.entity.Genre;

/**
 * Projection for {@link Genre}
 */
public interface GenreInfo {
    String getGenreName();
}