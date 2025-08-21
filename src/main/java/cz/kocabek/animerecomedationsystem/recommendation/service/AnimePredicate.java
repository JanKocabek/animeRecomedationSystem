package cz.kocabek.animerecomedationsystem.recommendation.service;

import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeOutDTO;

import java.util.Map;
import java.util.function.Predicate;

@FunctionalInterface
public interface AnimePredicate extends Predicate<Map.Entry<Long, AnimeOutDTO>> {
    boolean test(Map.Entry<Long, AnimeOutDTO> animeMap);
}
