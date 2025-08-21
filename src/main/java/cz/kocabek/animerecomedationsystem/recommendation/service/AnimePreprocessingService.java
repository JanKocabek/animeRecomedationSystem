package cz.kocabek.animerecomedationsystem.recommendation.service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.RecommendationConfig;
import cz.kocabek.animerecomedationsystem.recommendation.service.db.AnimeGenreService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnimePreprocessingService {

    final AnimeGenreService animeGenreService;
    final RecommendationConfig config;
    private final List<AnimePredicate> activeFilterList = new ArrayList<>();

    public AnimePreprocessingService(AnimeGenreService animeGenreService, RecommendationConfig config) {
        this.animeGenreService = animeGenreService;
        this.config = config;
    }

    public Map<Long, AnimeOutDTO> filterAnimeMap(Map<Long, AnimeOutDTO> animeMap) {
        selectFilters();
        return animeMap.entrySet().stream().filter(combineFilters())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (_, b) -> b));
    }

    private void selectFilters() {
        activeFilterList.clear();
        if (config.isOnlyInAnimeGenres()) {
            final var genres = animeGenreService.getGenresForAnime(config.getAnimeId());
            activeFilterList.add(entry -> entry.getValue().getGenres().stream().anyMatch(genres::contains));
        }
    }

    private AnimePredicate combineFilters() {
        return activeFilterList.stream().reduce(_ -> true, (f1, f2) -> entry -> f1.test(entry) && f2.test(entry), (f1, _) -> f1);
    }
}
