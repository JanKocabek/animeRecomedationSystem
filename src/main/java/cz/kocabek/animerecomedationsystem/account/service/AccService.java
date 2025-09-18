package cz.kocabek.animerecomedationsystem.account.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cz.kocabek.animerecomedationsystem.account.UserSessionData;
import cz.kocabek.animerecomedationsystem.account.dto.AccWatchlistShowDto;
import cz.kocabek.animerecomedationsystem.account.entity.AccWatchlist;
import cz.kocabek.animerecomedationsystem.account.entity.AppAccount;
import cz.kocabek.animerecomedationsystem.account.repository.AccWatchlistRepository;
import cz.kocabek.animerecomedationsystem.account.repository.AppAccRepository;
import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccService {
    private static final Logger logger = LoggerFactory.getLogger(AccService.class);
    private final AppAccRepository appAccRepository;
    private final EntityManager entityManager;
    private final AccWatchlistRepository accWatchlistRepository;
    private final UserSessionData userSessionData;

    public Integer getUserIdByUsername(String username) throws IllegalArgumentException {
        Optional<Integer> userId = appAccRepository.findAccountIdByUsername(username);
        return userId.orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void addAnimeToWatchlist(long animeId) {
        final var acc = entityManager.getReference(AppAccount.class, userSessionData.getUserId());
        final var anime = entityManager.getReference(Anime.class, animeId);
        accWatchlistRepository.save(AccWatchlist.create(acc, anime));
        logger.info("Anime with id {} added to watchlist", anime.getId());
    }

    public List<AccWatchlistShowDto> getWatchlistData() throws IllegalStateException {
        if (userSessionData.getUserId() == null) {
            throw new IllegalStateException("User isn't log-in or issue in the Auth process");
        }
        return accWatchlistRepository.getActiveWatchlistByAccountId(userSessionData.getUserId());
    }
}
