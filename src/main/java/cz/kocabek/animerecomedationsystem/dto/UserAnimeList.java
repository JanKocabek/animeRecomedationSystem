package cz.kocabek.animerecomedationsystem.dto;

import java.util.Map;

/**
 * Represents a user's anime list containing their ratings for different anime.
 * This record is used to store and transfer user-specific anime rating data
 * throughout the recommendation system.
 */
/**
 * id- The unique identifier of the user.
 */

/**
 * A map containing anime ratings where:
 * - Key (Long): The unique identifier of the anime
 * - Value (Integer): The user's rating for that anime
 */
public record UserAnimeList(Long id, Map<Long, Integer> animeList) {
}
