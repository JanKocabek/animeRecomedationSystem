package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
                        select u.id from User u join u.usersAnimeScores uas
                        where uas.id.animeId = ?1 order by uas.rating desc
            """)
    Slice<Long> retrieveDistinctUserIdsByAnimeSorted(@NonNull Long animeId, Pageable pageable);

}