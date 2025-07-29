package cz.kocabek.animerecomedationsystem.repository;

import cz.kocabek.animerecomedationsystem.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnimeRepository extends JpaRepository<Anime, Long> {

}