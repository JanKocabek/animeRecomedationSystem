package cz.kocabek.animerecomedationsystem.user.repository;

import cz.kocabek.animerecomedationsystem.user.entity.AccWatchlist;
import cz.kocabek.animerecomedationsystem.user.entity.AccWatchlistId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccWatchlistRepository extends JpaRepository<AccWatchlist, AccWatchlistId> {
}