package cz.kocabek.animerecomedationsystem.dto;

import cz.kocabek.animerecomedationsystem.entity.Anime;
import cz.kocabek.animerecomedationsystem.entity.AnimeGenre;

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