package cz.kocabek.animerecomedationsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link cz.kocabek.animerecomedationsystem.entity.Anime}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AnimeDto(Long id, @NotNull String name, Double score, String imageURL) implements Serializable {
}