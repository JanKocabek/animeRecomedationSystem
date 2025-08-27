package cz.kocabek.animerecomedationsystem.user.service;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import cz.kocabek.animerecomedationsystem.user.entity.AccWatchlist;
import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import cz.kocabek.animerecomedationsystem.user.repository.AccWatchlistRepository;
import cz.kocabek.animerecomedationsystem.user.repository.AppAccRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccService {
    private static final Logger logger = LoggerFactory.getLogger(AccService.class);
    AppAccRepository appAccRepository;
    EntityManager entityManager;
    AccWatchlistRepository accWatchlistRepository;

    public Long getUserIdByUsername(String username) throws IllegalArgumentException {
        Optional<Long> userId = appAccRepository.findAccountIdByUsername(username);
        return userId.orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void addAnimeToWatchlist(long animeId, String username) {
        final var acc = entityManager.getReference(AppAccount.class, getUserIdByUsername(username));
        final var anime = entityManager.getReference(Anime.class, animeId);
        accWatchlistRepository.save(new AccWatchlist(acc, anime));
        logger.info("Anime {} added to watchlist of user {}", anime.getName(), username);
    }
}
