package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAnimeScoreService {

    private static final Logger logger = LoggerFactory.getLogger(UserAnimeScoreService.class);

    UsersAnimeScoreRepository usersAnimeScoreRepository;
    public UserAnimeScoreService(UsersAnimeScoreRepository usersAnimeScoreRepository) {
        this.usersAnimeScoreRepository = usersAnimeScoreRepository;
    }

    public List<Long> getUserWithAnime(Long aniId) {
        return usersAnimeScoreRepository.retrieveDistinctUserIdsByAnimeSorted(aniId, PageRequest.of(0, 1000)).getContent();
    }

}
