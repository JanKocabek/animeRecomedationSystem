package cz.kocabek.animerecomedationsystem.account.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO for {@link cz.kocabek.animerecomedationsystem.account.entity.AccWatchlist}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AccWatchlistDto(Long idAnimeId, boolean inWatchlist) implements Serializable {
}