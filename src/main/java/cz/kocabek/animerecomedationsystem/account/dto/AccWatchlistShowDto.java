package cz.kocabek.animerecomedationsystem.account.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO for {@link cz.kocabek.animerecomedationsystem.account.entity.AccWatchlist}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AccWatchlistShowDto(
        Long idAnimeId,
        String animeName,
        String animeEnglishName,
        Double animeScore,
        String animeImageUrl) implements Serializable {
}