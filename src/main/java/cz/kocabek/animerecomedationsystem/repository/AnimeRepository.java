package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;


public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Iterable<Anime> findTop5ByGenres_GenreNameContainsIgnoreCaseAllIgnoreCase(@NonNull String genreName);

    @Query("select a.id from Anime a where a.name = ?1")
    Long getIdByName(@NonNull String name);
}