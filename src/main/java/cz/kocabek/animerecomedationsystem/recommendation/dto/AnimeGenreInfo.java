package cz.kocabek.animerecomedationsystem.recommendation.dto;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import cz.kocabek.animerecomedationsystem.recommendation.entity.AnimeGenre;

/**
 * Projection for {@link AnimeGenre}
 */
public interface AnimeGenreInfo {
    AnimeInfo getAnime();

    GenreInfo getGenre();

    /**
     * Projection for {@link Anime}
     */
    interface AnimeInfo {
        Long getId();
    }
}