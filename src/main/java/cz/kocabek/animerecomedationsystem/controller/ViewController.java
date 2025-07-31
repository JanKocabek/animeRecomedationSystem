package cz.kocabek.animerecomedationsystem.controller;

import cz.kocabek.animerecomedationsystem.entity.Anime;
import cz.kocabek.animerecomedationsystem.entity.UserDto;
import cz.kocabek.animerecomedationsystem.service.AnimeService;
import cz.kocabek.animerecomedationsystem.service.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

    AnimeService animeService;
    UserServices userServices;

    public ViewController(AnimeService animeService, UserServices userServices) {
        this.animeService = animeService;
        this.userServices = userServices;
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
    @GetMapping("/anime/who/{name}")
    public ResponseEntity<Iterable<UserDto>> getAllUsersWhoWatched(@PathVariable String name) {
        Long id = animeService.getAnimeIdByName(name);
        Iterable<UserDto> users = userServices.getAllUsersWhoRatedGiven(id);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }
}
