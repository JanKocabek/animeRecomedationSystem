package cz.kocabek.animerecomedationsystem.recommendation.dto;

import java.io.Serializable;

import cz.kocabek.animerecomedationsystem.recommendation.entity.UsersAnimeScore;

/**
 * DTO for {@link UsersAnimeScore}
 */

public record UsersAnimeScoreDto(Long userId, Long animeId, Integer rating) implements Serializable {
}