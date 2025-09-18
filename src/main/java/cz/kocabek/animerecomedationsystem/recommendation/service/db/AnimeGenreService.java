package cz.kocabek.animerecomedationsystem.recommendation.service.db;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeGenreInfo;
import cz.kocabek.animerecomedationsystem.recommendation.repository.AnimeGenreRepository;

@Service
public class AnimeGenreService {

    AnimeGenreRepository repository;

    public AnimeGenreService(AnimeGenreRepository repository) {
        this.repository = repository;
    }

    public Map<Long, List<String>> getGenresByAnimeIds(Collection<Long> animeIds) {
        final var animeGenreInfos = repository.getById_AnimeIdIn(animeIds);
        return groupGenrePerAnime(animeGenreInfos);
    }

    public List<String> getGenresForAnime(Long animeId) {
        return repository.getAnimeGenresByAnimeId(animeId);
    }

    private Map<Long, List<String>> groupGenrePerAnime(List<AnimeGenreInfo> genreInfos) {
        return genreInfos.stream().collect(
                Collectors.groupingBy(info -> info.getAnime().getId(),
                        Collectors.mapping(info -> info.getGenre().getGenreName(),
                                Collectors.toList())
                ));
    }
}
