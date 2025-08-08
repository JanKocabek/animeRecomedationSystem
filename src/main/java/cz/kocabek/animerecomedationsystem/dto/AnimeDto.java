package cz.kocabek.animerecomedationsystem.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link cz.kocabek.animerecomedationsystem.entity.Anime}
 */

public record AnimeDto(Long id, @NotNull String name, Double score, String imageURL) implements Serializable {
}