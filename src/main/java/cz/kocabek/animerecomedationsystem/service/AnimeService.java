package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeDto;
import cz.kocabek.animerecomedationsystem.entity.Anime;
import cz.kocabek.animerecomedationsystem.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimeService {

    AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public Anime getAnimeById(Long id) {
        return animeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Anime not found"));
    }

    public Iterable<Anime> getAnimeByGenre(String genre){
        return animeRepository.findTop5ByGenres_GenreNameContainsIgnoreCaseAllIgnoreCase(genre);
    }

    public Long getAnimeIdByName(String name){
        return animeRepository.getAnimeIdByName(name);
    }

    public List<AnimeDto> getListAnimeFromIds(List<Long> ids){
        return animeRepository.getAnimeSetOrderByScore(ids);
    }

}
