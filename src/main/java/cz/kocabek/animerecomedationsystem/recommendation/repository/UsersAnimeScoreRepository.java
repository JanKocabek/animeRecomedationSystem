package cz.kocabek.animerecomedationsystem.recommendation.repository;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import cz.kocabek.animerecomedationsystem.recommendation.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.recommendation.entity.UsersAnimeScore;
import cz.kocabek.animerecomedationsystem.recommendation.entity.UsersAnimeScoreId;

public interface UsersAnimeScoreRepository extends JpaRepository<UsersAnimeScore, UsersAnimeScoreId> {

    @Query("""
            select new cz.kocabek.animerecomedationsystem.recommendation.dto.UsersAnimeScoreDto(u.id.userId,u.id.animeId,u.rating)  from UsersAnimeScore u
            where u.id.userId in :userIds and u.id.animeId != :animeId
            """)
    Slice<UsersAnimeScoreDto> getUsersListRatedAnime(@Param("userIds") @NonNull Collection<Long> userIds, @Param("animeId") Long animeId, Pageable pageable);

    @Query("""
            select uas.id.userId from UsersAnimeScore uas
                    where uas.id.animeId =:animeid
                    AND uas.rating between :minrating and :maxrating
            """)
    Slice<Long> findUsersIdByAnimeIdAndRatingRange(@NonNull @Param("animeid") Long animeId, @Param("minrating") int minrating, @Param("maxrating") int maxrating, Pageable pageable);
}
