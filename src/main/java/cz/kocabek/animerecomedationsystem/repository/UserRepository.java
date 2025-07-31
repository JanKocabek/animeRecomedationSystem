package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("""
//select u.username from User u join fetch usa.  from u.usersAnimeScores usa
//""")

    //getting users who rated the anime on input and list of anime's they rated
    @EntityGraph(attributePaths = {"usersAnimeScores.anime.genres"})
    @Query("""
            select u from User u join fetch u.usersAnimeScores usersAnimeScores
                        where usersAnimeScores.anime.id = ?1
            """)
    List<User> findByUsersAnimeScores_Anime_Id(@NonNull Long id);
}