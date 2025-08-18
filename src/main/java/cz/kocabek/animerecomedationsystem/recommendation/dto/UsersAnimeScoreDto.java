package cz.kocabek.animerecomedationsystem.recommendation.dto;

import cz.kocabek.animerecomedationsystem.recommendation.entity.UsersAnimeScore;

import java.io.Serializable;

/**
 * DTO for {@link UsersAnimeScore}
 */

public record UsersAnimeScoreDto(Long userId, Long animeId, String animeName, Integer rating) implements Serializable {
}