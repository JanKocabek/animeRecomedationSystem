package cz.kocabek.animerecomedationsystem.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link cz.kocabek.animerecomedationsystem.user.entity.AccWatchlist}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AccWatchlistDto(Long idAnimeId, boolean inWatchlist) implements Serializable {
}