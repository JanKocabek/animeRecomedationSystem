package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.entity.UsersAnimeScore;
import cz.kocabek.animerecomedationsystem.entity.UsersAnimeScoreId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface UsersAnimeScoreRepository extends JpaRepository<UsersAnimeScore, UsersAnimeScoreId> {
    @Query("""
            select new cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto(u.id.userId,u.id.animeId,u.animeTitle,u.rating)  from UsersAnimeScore u
            where u.id.userId in :userIds and u.id.animeId is not null
            order by u.id.userId""")
    Slice<UsersAnimeScoreDto> getUsersListRatedAnime(@Param("userIds") @NonNull Collection<Long> userIds, @Param("animeId")  Pageable pageable);
}