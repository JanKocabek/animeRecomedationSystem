package cz.kocabek.animerecomedationsystem.recommendation.dto;

import java.util.Map;

/**
 * Represents a user's detail list containing their ratings for different detail.
 * This record is used to store and transfer user-specific detail rating data
 * throughout the recommendation system.
 * -------------------------------------
 * id - The unique identifier of the user.
 * animeList -
 * A map containing detail ratings where:
 * - Key (Long): The unique identifier of the detail
 * - Value (Integer): The user's rating for that detail
 **/
public record UserAnimeList(Long id, Map<Long, Integer> animeList) {
}
