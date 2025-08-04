package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.dto.AnimeDto;
import cz.kocabek.animerecomedationsystem.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;


public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Iterable<Anime> findTop5ByGenres_GenreNameContainsIgnoreCaseAllIgnoreCase(@NonNull String genreName);

    @Query("select a.id from Anime a where a.name = ?1")
    Long getAnimeIdByName(@NonNull String name);

    @Query("select new cz.kocabek.animerecomedationsystem.dto.AnimeDto(a.id,a.name,a.score,a.imageURL)  from Anime a where a.id  in :animeIds order by a.score DESC")
    List<AnimeDto> getAnimeSetOrderByScore(@Param("animeIds") @NonNull Collection<Long> animeIds);

}