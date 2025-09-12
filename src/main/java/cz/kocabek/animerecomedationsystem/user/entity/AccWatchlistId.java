package cz.kocabek.animerecomedationsystem.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AccWatchlistId implements Serializable {
    private static final long serialVersionUID = 3953028786396392471L;
    @NotNull
    @Column(name = "acc_id", nullable = false)
    private Integer accId;

    @NotNull
    @Column(name = "anime_id", nullable = false)
    private Long animeId;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        AccWatchlistId entity = (AccWatchlistId) o;
        return Objects.equals(this.animeId, entity.animeId) &&
                Objects.equals(this.accId, entity.accId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animeId, accId);
    }

}