package cz.kocabek.animerecomedationsystem.recommendation.service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.RecommendationConfig;
import cz.kocabek.animerecomedationsystem.recommendation.service.db.AnimeGenreService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnimePreprocessingService {

    AnimeGenreService animeGenreService;
    RecommendationConfig config;

    AnimePreprocessingService(AnimeGenreService animeGenreService, RecommendationConfig config) {
        this.animeGenreService = animeGenreService;
        this.config = config;
    }

    public Map<Long, AnimeOutDTO> filterAnime(Map<Long, AnimeOutDTO> animeMap) {
        final var map = new HashMap<>(animeMap);
        if (config.isOnlyInAnimeGenres()) {
            final var genres = animeGenreService.getGenresForAnime(config.getAnimeId());
            filterOutByInputGenres(map, genres);
        }
        return map;
    }

    private void filterOutByInputGenres(Map<Long, AnimeOutDTO> map, List<String> genres) {
        map.entrySet().removeIf(entry ->
                entry.getValue().getGenres().stream().noneMatch(genres::contains));
    }
}
