package cz.kocabek.animerecomedationsystem.controller;

import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import cz.kocabek.animerecomedationsystem.entity.Anime;
import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import cz.kocabek.animerecomedationsystem.service.AnimeService;
import cz.kocabek.animerecomedationsystem.service.RecommendService;
import cz.kocabek.animerecomedationsystem.service.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ViewController {

    AnimeService animeService;
    UserServices userServices;
    UsersAnimeScoreRepository usersAnimeScoreRepository;
    RecommendService recommendService;

    public ViewController(AnimeService animeService, UserServices userServices, UsersAnimeScoreRepository usersAnimeScoreRepository, RecommendService recommendService) {
        this.animeService = animeService;
        this.userServices = userServices;
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
        this.recommendService = recommendService;
    }

    @ResponseBody
    @GetMapping("/anime/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        Anime anime = animeService.getAnimeById(id);
        return ResponseEntity.ok(anime);
    }

    @ResponseBody
    @GetMapping("/anime/genre/{genre}")
    public ResponseEntity<Iterable<Anime>> getAnimeByGenre(@PathVariable String genre) {
        Iterable<Anime> anime = animeService.getAnimeByGenre(genre);
        return ResponseEntity.ok(anime);
    }

    @ResponseBody
    @GetMapping("/anime/recommend/{name}")
    public ResponseEntity<List<UserAnimeList>> getAnimeRecommendation(@PathVariable String name) {
        List<UserAnimeList> data = recommendService.getAnimeRecommendation(name);
        return ResponseEntity.ok(data);
    }
}
