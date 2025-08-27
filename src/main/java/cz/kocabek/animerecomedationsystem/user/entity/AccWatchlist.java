package cz.kocabek.animerecomedationsystem.user.entity;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import jakarta.persistence.*;
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

    public AccWatchlist(AppAccount acc, Anime anime) {
        this.acc=acc;
        this.anime=anime;
        AccWatchlistId id = new AccWatchlistId();
        id.setAccId(acc.getId());
        id.setAnimeId(anime.getId());
        this.id = id;
    }

    public AccWatchlist() {
    }
}