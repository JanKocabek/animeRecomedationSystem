package cz.kocabek.animerecomedationsystem.recommendation.dto;

import jakarta.validation.constraints.NotEmpty;

public record InputDTO(@NotEmpty(message = "enter at least one character")
                       String animeName,
                       int minRating,
                       int maxUsers,
                       boolean onlyInAnimeGenres) {
}
