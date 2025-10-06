package cz.kocabek.animerecomedationsystem.account.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cz.kocabek.animerecomedationsystem.account.UserSessionData;
import cz.kocabek.animerecomedationsystem.account.dto.AccWatchlistDto;
import cz.kocabek.animerecomedationsystem.account.repository.AccWatchlistRepository;
import cz.kocabek.animerecomedationsystem.recommendation.dto.RecommendationDTO;

@Service
public class WatchListService {

    AccWatchlistRepository accWatchlistRepository;
    UserSessionData userSessionData;

    public WatchListService(AccWatchlistRepository accWatchlistRepository, UserSessionData userSessionData) {
        this.accWatchlistRepository = accWatchlistRepository;
        this.userSessionData = userSessionData;
    }

    public Map<Long, AccWatchlistDto> getUserWatchListMap(Integer userId) {
        return accWatchlistRepository.getWatchlistByAccId(userId)
                .stream().collect(Collectors.toMap(AccWatchlistDto::idAnimeId, w -> w));
    }

    public void setWatchlistButtons(RecommendationDTO dto) {
        final var watchlist = getUserWatchListMap(userSessionData.getUserId());
        dto.getRecommendedAnime().forEach(anime -> {
            final var entry = watchlist.get(anime.getAnimeId());
            if (entry != null) {
                anime.setInWatchList(entry.inWatchlist());
            }
        });
    }

    public void removeFromWatchlist(Long animeId) {
        //todo make better exception handling
        if (accWatchlistRepository.updateWatchlistStatus(animeId, userSessionData.getUserId(), false) == 0) {
            throw new IllegalStateException("Anime with id " + animeId + " is not in watchlist");
        }
    }

    public void reAddToWatchlist(Long animeId) {
        //todo make better exception handling
        if (accWatchlistRepository.updateWatchlistStatus(animeId, userSessionData.getUserId(), true) == 0) {
            throw new IllegalStateException("Anime with id " + animeId + " is already in watchlist");
        }
    }

}
