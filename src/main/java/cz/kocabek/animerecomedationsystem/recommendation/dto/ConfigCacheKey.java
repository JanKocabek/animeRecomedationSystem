package cz.kocabek.animerecomedationsystem.recommendation.dto;

public record ConfigCacheKey(String animeName,
        Long animeId,
        int minScore,
        int maxUsers,
        boolean onlyInAnimeGenres) {

}
