package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeGenreInfo;
import cz.kocabek.animerecomedationsystem.repository.AnimeGenreRepository;
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

    public Map<Long, List<String>> getGenreForAnime(Collection<Long> animeIds) {
        final var animeGenreInfos = repository.getById_AnimeIdIn(animeIds);
        return groupGenrePerAnime(animeGenreInfos);
    }

    private Map<Long, List<String>> groupGenrePerAnime(List<AnimeGenreInfo> genreInfos) {
        return genreInfos.stream().collect(
                Collectors.groupingBy(info -> info.getAnime().getId(),
                        Collectors.mapping(info -> info.getGenre().getGenreName(),
                                Collectors.toList())
               ));
    }
}
