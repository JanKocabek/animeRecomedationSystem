package cz.kocabek.animerecomedationsystem.recommendation.service.db;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeDto;
import cz.kocabek.animerecomedationsystem.recommendation.entity.Anime;
import cz.kocabek.animerecomedationsystem.recommendation.repository.AnimeRepository;
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

    public Long getAnimeIdByName(String animeName) throws ValidationException {
        final var name = animeName.trim().toLowerCase();
        if (name.isBlank() || name.equals(" ")) {
            throw new ValidationException("Anime name cannot be blank");
        }
        return animeRepository.getAnimeIdByName(name)
                .orElseGet(() -> animeRepository.getAnimeIdByEnglishName(name)
                        .orElseThrow(() -> new ValidationException("This anime was not found.%nTry again with different name".formatted())));
    }

    public List<AnimeDto> getListAnimeFromIds(Collection<Long> ids) {
        return animeRepository.getAnimeListOrderByScore(ids);
    }

    public Collection<String> getAnimeNamesByIds(Collection<Long> animeIds) {
        return animeRepository.getAnimeNamesByIds(animeIds);
    }
    public String getAnimeNameById(Long id) {
        return animeRepository.getAnimeNameById(id);
    }
}
