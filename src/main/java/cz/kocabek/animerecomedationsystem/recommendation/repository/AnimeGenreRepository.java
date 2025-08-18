package cz.kocabek.animerecomedationsystem.recommendation.repository;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeGenreInfo;
import cz.kocabek.animerecomedationsystem.recommendation.entity.AnimeGenre;
import cz.kocabek.animerecomedationsystem.recommendation.entity.AnimeGenreId;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

public interface AnimeGenreRepository extends Repository<AnimeGenre, AnimeGenreId> {
    List<AnimeGenreInfo> getById_AnimeIdIn(@NonNull Collection<Long> animeId);
}