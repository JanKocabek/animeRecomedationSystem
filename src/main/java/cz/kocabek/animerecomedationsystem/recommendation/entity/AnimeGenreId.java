package cz.kocabek.animerecomedationsystem.recommendation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AnimeGenreId implements Serializable {
    private static final long serialVersionUID = -8817145053069260071L;
    @Column(name = "anime_id", columnDefinition = "int UNSIGNED not null")
    private Long animeId;

    @NotNull
    @Column(name = "genre_id", nullable = false)
    private Integer genreId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnimeGenreId entity = (AnimeGenreId) o;
        return Objects.equals(this.genreId, entity.genreId) &&
               Objects.equals(this.animeId, entity.animeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, animeId);
    }

}