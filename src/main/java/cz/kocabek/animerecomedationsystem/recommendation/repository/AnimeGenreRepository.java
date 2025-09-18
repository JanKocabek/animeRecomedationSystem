package cz.kocabek.animerecomedationsystem.recommendation.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeGenreInfo;
import cz.kocabek.animerecomedationsystem.recommendation.entity.AnimeGenre;
import cz.kocabek.animerecomedationsystem.recommendation.entity.AnimeGenreId;

public interface AnimeGenreRepository extends Repository<AnimeGenre, AnimeGenreId> {

    List<AnimeGenreInfo> getById_AnimeIdIn(@NonNull Collection<Long> animeId);

    @Query("""
            SELECT g.genreName from AnimeGenre ag
            join ag.genre g
                where ag.id.animeId = ?1
            """)
    List<String> getAnimeGenresByAnimeId(Long animeId);
}
