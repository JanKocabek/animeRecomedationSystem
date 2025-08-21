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


    public AnimePreprocessingService(AnimeGenreService animeGenreService, RecommendationConfig config) {
        this.animeGenreService = animeGenreService;
        this.config = config;
    }

    public Map<Long, AnimeOutDTO> filterAnimeMap(Map<Long, AnimeOutDTO> animeMap) {
        final List<AnimePredicate> selectedFilters = selectFilters();
        return animeMap.entrySet().stream().filter(combineFilters(selectedFilters))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (_, b) -> b));
    }

    private List<AnimePredicate> selectFilters() {
        final var filters = new ArrayList<AnimePredicate>();
        if (config.isOnlyInAnimeGenres()) {
            final var genres = animeGenreService.getGenresForAnime(config.getAnimeId());
            filters.add(entry -> entry.getValue().getGenres().stream().anyMatch(genres::contains));
        }
        return filters;
    }

    private AnimePredicate combineFilters(List<AnimePredicate> activeFilterList) {
        return activeFilterList.stream().reduce(_ -> true,
                (f1, f2) -> entry -> f1.test(entry) && f2.test(entry),
                (f1, _) -> f1);
    }
}
