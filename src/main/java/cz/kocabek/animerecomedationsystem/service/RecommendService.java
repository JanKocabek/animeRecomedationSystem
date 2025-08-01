package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendService {

    AnimeService animeService;
    UserServices userServices;
    UsersAnimeScoreRepository usersAnimeScoreRepository;

    public RecommendService(AnimeService animeService, UserServices userServices, UsersAnimeScoreRepository usersAnimeScoreRepository) {
        this.animeService = animeService;
        this.userServices = userServices;
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
    }

    public List<UsersAnimeScoreDto> getAnimeRecommendation(String name) {
        Long id = animeService.getAnimeIdByName(name);
        List<Long> usersId = userServices.getUserWithAnime(id);
        return usersAnimeScoreRepository.getUsersAnimeScoresById_UserIdInAndId_AnimeIdNotNull(usersId, PageRequest.of(0, 20));
    }

}
