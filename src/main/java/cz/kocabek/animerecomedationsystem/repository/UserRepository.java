package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
                        select u.id from User u join u.usersAnimeScores uas
                        where uas.id.animeId = ?1 order by uas.rating desc
            """)
    List<Long> retrieveDistinctUserIdsByAnimeSorted(@NonNull Long animeId);

}