package cz.kocabek.animerecomedationsystem.dto;

import java.util.Map;


public record UserAnimeList(Long id, Map<Long, Integer> animeList) {

}
