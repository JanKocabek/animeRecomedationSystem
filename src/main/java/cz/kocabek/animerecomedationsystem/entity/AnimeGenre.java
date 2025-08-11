package cz.kocabek.animerecomedationsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "anime_genres", schema = "mydatabase", indexes = {
        @Index(name = "genre_id", columnList = "genre_id")
})
public class AnimeGenre {
    @EmbeddedId
    private AnimeGenreId id;

    @MapsId("animeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @MapsId("genreId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

}