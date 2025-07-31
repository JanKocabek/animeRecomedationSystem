package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.dto.UsersAnimeScoreDto;
import cz.kocabek.animerecomedationsystem.entity.UsersAnimeScore;
import cz.kocabek.animerecomedationsystem.entity.UsersAnimeScoreId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

public interface UsersAnimeScoreRepository extends JpaRepository<UsersAnimeScore, UsersAnimeScoreId> {
    List<UsersAnimeScoreDto> getUsersAnimeScoresById_UserIdInAndId_AnimeIdNotNull(@NonNull Collection<Long> userIds, Pageable pageable);
}

