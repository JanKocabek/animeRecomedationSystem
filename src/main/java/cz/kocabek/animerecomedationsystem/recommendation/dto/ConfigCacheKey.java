package cz.kocabek.animerecomedationsystem.recommendation.dto;

public record ConfigCacheKey(
        Long animeId,
        int minScore,
        int maxUsers,
        boolean onlyInAnimeGenres) {

}
