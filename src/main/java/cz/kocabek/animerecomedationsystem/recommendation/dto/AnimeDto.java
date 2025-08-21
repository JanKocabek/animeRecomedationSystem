package cz.kocabek.animerecomedationsystem.recommendation.dto;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link Anime}
 */


public record AnimeDto(Long id, @NotNull String name, Double score, String imageURL) implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
}