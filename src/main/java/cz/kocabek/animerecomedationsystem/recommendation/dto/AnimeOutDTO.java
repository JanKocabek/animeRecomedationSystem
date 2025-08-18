package cz.kocabek.animerecomedationsystem.recommendation.dto;

import lombok.Data;

@Data
public class AnimeOutDTO {
    private Long animeId;
    private Double averageRating;
    private int occurrences;
    private double percentageOccurrences;
    private AnimeDto animeInfo;
    private double sumOfRatings;

    public AnimeOutDTO(double sumOfRatings, int occurrences) {
        this.sumOfRatings = sumOfRatings;
        this.occurrences = occurrences;
        this.averageRating = null;
    }

    public void incrementOccurrences() {
        this.occurrences++;
    }

    public void addToRatingSum(double rating) {
        this.sumOfRatings += rating;
    }

    public void calculateAverageRating() {
        this.averageRating = this.sumOfRatings / this.occurrences;
    }
}
