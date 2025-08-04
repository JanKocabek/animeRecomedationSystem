package cz.kocabek.animerecomedationsystem.controller;

import cz.kocabek.animerecomedationsystem.dto.AnimeDto;
import cz.kocabek.animerecomedationsystem.entity.Anime;
import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import cz.kocabek.animerecomedationsystem.service.AnimeService;
import cz.kocabek.animerecomedationsystem.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestPointController {

    AnimeService animeService;
    UsersAnimeScoreRepository usersAnimeScoreRepository;
    RecommendationService recommendationService;

    public RestPointController(AnimeService animeService, UsersAnimeScoreRepository usersAnimeScoreRepository, RecommendationService recommendationService) {
        this.animeService = animeService;
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
        this.recommendationService = recommendationService;
    }


    @GetMapping("/anime/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        final var anime = animeService.getAnimeById(id);
        return ResponseEntity.ok(anime);
    }


    @GetMapping("/anime/genre/{genre}")
    public ResponseEntity<Iterable<Anime>> getAnimeByGenre(@PathVariable String genre) {
        final var anime = animeService.getAnimeByGenre(genre);
        return ResponseEntity.ok(anime);
    }


    @GetMapping("/anime/recommend/{name}")
    public ResponseEntity<List<AnimeDto>> getAnimeRecommendation(@PathVariable String name) {
        final var data = recommendationService.getAnimeRecommendation(name);
        return ResponseEntity.ok(data);
    }
}
