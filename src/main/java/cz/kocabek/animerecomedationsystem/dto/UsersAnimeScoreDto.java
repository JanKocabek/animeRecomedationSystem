package cz.kocabek.animerecomedationsystem.dto;

import cz.kocabek.animerecomedationsystem.entity.UsersAnimeScore;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link UsersAnimeScore}
 */
@Value
public class UsersAnimeScoreDto implements Serializable {
    AnimeDto anime;
    Integer rating;
}