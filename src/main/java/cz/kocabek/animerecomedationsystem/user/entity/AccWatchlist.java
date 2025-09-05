package cz.kocabek.animerecomedationsystem.user.entity;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault("true")
    private boolean inWatchlist;

    public AccWatchlist(AppAccount acc, Anime anime) {
        this.acc=acc;
        this.anime=anime;
        AccWatchlistId watchlistId = new AccWatchlistId();
        watchlistId.setAccId(acc.getId());
        watchlistId.setAnimeId(anime.getId());
        this.id = watchlistId;
    }

    public AccWatchlist() {
    }
}