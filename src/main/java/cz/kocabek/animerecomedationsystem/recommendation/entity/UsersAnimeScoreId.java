package cz.kocabek.animerecomedationsystem.recommendation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UsersAnimeScoreId implements Serializable {
    @Serial
    private static final long serialVersionUID = 3479452094872909133L;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "anime_id")
    private Long animeId;

}