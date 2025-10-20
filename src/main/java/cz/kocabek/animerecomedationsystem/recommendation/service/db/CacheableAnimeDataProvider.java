package cz.kocabek.animerecomedationsystem.recommendation.service.db;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.kocabek.animerecomedationsystem.recommendation.dto.ConfigCacheKey;
import cz.kocabek.animerecomedationsystem.recommendation.repository.UsersAnimeScoreRepository;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.ConfigConstant;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CacheableAnimeDataProvider {

    private final UsersAnimeScoreRepository usersAnimeScoreRepository;

    @Cacheable(value = "usersWithAnimeScore")
    public List<Long> getUsersIdWhoRatedGivenAnime(ConfigCacheKey config) {
        return usersAnimeScoreRepository.findUsersIdByAnimeIdAndRatingRange(config.animeId(), config.minScore(),
                ConfigConstant.MAX_INPUT_SCORE,
                PageRequest.of(0, config.maxUsers())).getContent();
    }

}
