package cz.kocabek.animerecomedationsystem.dto;

import java.io.Serializable;

/**
 * DTO for {@link cz.kocabek.animerecomedationsystem.entity.UsersAnimeScore}
 */

public record UsersAnimeScoreDto(Long userId, Long animeId, String animeName, Integer rating) implements Serializable {
}