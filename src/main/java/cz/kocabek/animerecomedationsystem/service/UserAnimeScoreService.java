package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.repository.UsersAnimeScoreRepository;
import cz.kocabek.animerecomedationsystem.service.config.SystemConfConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        return usersAnimeScoreRepository.retrieveDistinctUserIdsByAnimeSorted(aniId, PageRequest.of(0, SystemConfConst.USERIDS_SIZE_Q_PAGE)).getContent();
    }

    //fetching anime ranking records from a given userIdList and anime ID
    public Slice<UsersAnimeScoreDto> fetchRatedAnimeByUsers(List<Long> usersId, Long animeId, Pageable pageable) {
        final var ratedAnimeData = usersAnimeScoreRepository.getUsersListRatedAnime(usersId, animeId, pageable);
        logger.debug("size of fetch data:  {}", ratedAnimeData.getContent().size());
        //logger.debug("size of fetch Animedata: {} vs asked: {}", ratedAnimeData.getNumberOfElements(), limit);
        return ratedAnimeData;
    }

}
