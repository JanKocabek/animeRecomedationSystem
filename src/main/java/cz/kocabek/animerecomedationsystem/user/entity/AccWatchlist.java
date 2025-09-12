package cz.kocabek.animerecomedationsystem.user.entity;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "acc_watchlist", schema = "mydatabase")
public class AccWatchlist {
    @EmbeddedId
    private AccWatchlistId id;

    @MapsId("accId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "acc_id", nullable = false)
    private AppAccount acc;

    @MapsId("animeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @Column(name = "inWatchlist")
    @NotNull
    private boolean inWatchlist;

    public AccWatchlist(Integer accId, Long animeId) {
        this.id = new AccWatchlistId(accId,animeId);
        this.inWatchlist = true;
    }

    public AccWatchlist() {
    }
}