package cz.kocabek.animerecomedationsystem.recommendation.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing output data related to concrete anime in the recommendation.
 * <p>
 * This class stores aggregated data about anime, including its unique ID, average rating,
 * number of occurrences, percentage of total occurrences, and detailed information.
 */
@Data
public class AnimeOutDTO {

    /**
     * The unique identifier of the anime.
     */
    private Long animeId;

    /**
     * The average rating of the anime, calculated from user ratings.
     */
    private Double averageRating;

    /**
     * The number of times the anime has been encountered or recommended.
     */
    private int occurrences;

    /**
     * The percentage share of occurrences compared to other anime.
     */
    private double percentageOccurrences;

    /**
     * Detailed information about the anime.
     */
    private AnimeDto animeInfo;

    /**
     * The total sum of ratings received for this anime.
     */
    private double sumOfRatings;

    private List<String> genres;

    /**
     * Constructs a new {@code AnimeOutDTO} with the specified sum of ratings and number of occurrences.
     * The average rating is initialized to {@code null}.
     *
     * @param sumOfRatings the total sum of ratings for the anime
     * @param occurrences the number of times the anime has been recommended
     */
    public AnimeOutDTO(double sumOfRatings, int occurrences) {
        this.sumOfRatings = sumOfRatings;
        this.occurrences = occurrences;
        this.averageRating = null;
    }

    /**
     * Increments the number of occurrences by one.
     */
    public void incrementOccurrences() {
        this.occurrences++;
    }

    /**
     * Adds a given rating to the total sum of ratings.
     *
     * @param rating the rating to add
     */
    public void addToRatingSum(double rating) {
        this.sumOfRatings += rating;
    }

    /**
     * Calculates the average rating by dividing the total sum of ratings
     * by the number of occurrences, and updates the {@code averageRating} field.
     */
    public void calculateAverageRating() {
        this.averageRating = this.sumOfRatings / this.occurrences;
    }
}