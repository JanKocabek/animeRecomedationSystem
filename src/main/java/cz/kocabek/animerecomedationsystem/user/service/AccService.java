package cz.kocabek.animerecomedationsystem.user.service;

import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import cz.kocabek.animerecomedationsystem.user.UserSessionData;
import cz.kocabek.animerecomedationsystem.user.dto.AccWatchlistShowDto;
import cz.kocabek.animerecomedationsystem.user.entity.AccWatchlist;
import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import cz.kocabek.animerecomedationsystem.user.repository.AccWatchlistRepository;
import cz.kocabek.animerecomedationsystem.user.repository.AppAccRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        accWatchlistRepository.save(new AccWatchlist(acc, anime));
        logger.info("Anime {} added to watchlist", anime.getName());
    }

    public List<AccWatchlistShowDto> getWatchlistData() throws IllegalStateException {
        if (userSessionData.getUserId() == null) {
            throw new IllegalStateException("User isn't log-in or issue in the Auth process");
        }
        return accWatchlistRepository.getActiveWatchlistByAccountId(userSessionData.getUserId());
    }
}
