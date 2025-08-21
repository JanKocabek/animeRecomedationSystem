package cz.kocabek.animerecomedationsystem.recommendation.service.db;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeGenreInfo;
import cz.kocabek.animerecomedationsystem.recommendation.repository.AnimeGenreRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnimeGenreService {

    AnimeGenreRepository repository;

    public AnimeGenreService(AnimeGenreRepository repository) {
        this.repository = repository;
    }

    public Map<Long, List<String>> getGenresForAnime(Collection<Long> animeIds) {
        final var animeGenreInfos = repository.getById_AnimeIdIn(animeIds);
        return groupGenrePerAnime(animeGenreInfos);
    }

    public List<String> getGenresForAnime(Long animeId) {
        final var genres = repository.getAnimeGenresByAnimeId(animeId);
        return genres.stream().map(info -> info.getGenre().getGenreName()).toList();
    }

    private Map<Long, List<String>> groupGenrePerAnime(List<AnimeGenreInfo> genreInfos) {
        return genreInfos.stream().collect(
                Collectors.groupingBy(info -> info.getAnime().getId(),
                        Collectors.mapping(info -> info.getGenre().getGenreName(),
                                Collectors.toList())
               ));
    }
}
