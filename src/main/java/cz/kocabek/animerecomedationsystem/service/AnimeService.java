package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeDto;
import cz.kocabek.animerecomedationsystem.entity.Anime;
import cz.kocabek.animerecomedationsystem.repository.AnimeRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AnimeService {

    AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public Anime getAnimeById(Long id) throws IllegalArgumentException {
        return animeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Anime not found"));
    }

    public Iterable<Anime> getAnimeByGenre(String genre) {
        return animeRepository.findTop5ByGenres_GenreNameContainsIgnoreCaseAllIgnoreCase(genre);
    }

    public Long getAnimeIdByName(String name) throws ValidationException {
        return animeRepository.getAnimeIdByName(name.toLowerCase())
                .orElse(animeRepository.getAnimeIdByEnglishName(name)
                        .orElseThrow(() -> new ValidationException("Anime not found")));

    }

    public List<AnimeDto> getListAnimeFromIds(Collection<Long> ids) {
        return animeRepository.getAnimeSetOrderByScore(ids);
    }
}
