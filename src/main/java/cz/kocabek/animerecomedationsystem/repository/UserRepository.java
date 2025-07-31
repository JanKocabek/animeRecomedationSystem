package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            select distinct u.id from User u inner join u.usersAnimeScores usersAnimeScores
            where usersAnimeScores.id.animeId = ?1""")
    List<Long> findUserIdDistinctByUsersAnimeScores_Id_UserId(@NonNull Long animeId);

}