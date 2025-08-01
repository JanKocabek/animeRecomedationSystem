package cz.kocabek.animerecomedationsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_anime_score")
public class UsersAnimeScore {
    @EmbeddedId
    private UsersAnimeScoreId id;

    @JsonBackReference
    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("animeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @Column(name = "Anime Title")
    private String animeTitle;

    @Column(name = "rating")
    private Integer rating;

}