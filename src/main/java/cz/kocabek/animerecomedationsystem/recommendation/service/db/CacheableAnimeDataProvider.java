package cz.kocabek.animerecomedationsystem.recommendation.service.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.kocabek.animerecomedationsystem.recommendation.dto.ConfigCacheKey;
import cz.kocabek.animerecomedationsystem.recommendation.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.recommendation.repository.UsersAnimeScoreRepository;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.ConfigConstant;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CacheableAnimeDataProvider {

    private static final Logger logger = LoggerFactory.getLogger(CacheableAnimeDataProvider.class);

    private final UsersAnimeScoreRepository usersAnimeScoreRepository;

    @Cacheable(value = "usersWithAnimeScore")
    public List<Long> getUsersIdWhoRatedGivenAnime(ConfigCacheKey config) {
        return usersAnimeScoreRepository.findUsersIdByAnimeIdAndRatingRange(config.animeId(), config.minScore(),
                ConfigConstant.MAX_INPUT_SCORE,
                PageRequest.of(0, config.maxUsers())).getContent();
    }

    @Cacheable(value = "usersListRatedAnime")
     //fetching detail ranking records from a given userIdList and detail ID
    public Slice<UsersAnimeScoreDto> fetchRatedAnimeByUsers(List<Long> usersId, ConfigCacheKey config) {
        final var ratedAnimeData = usersAnimeScoreRepository.getUsersListRatedAnime(usersId, config.animeId(), Pageable.unpaged());
        logger.debug("size of fetch data:  {}", ratedAnimeData.getContent().size());
        return ratedAnimeData;
    }

}
