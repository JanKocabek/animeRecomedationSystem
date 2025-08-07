package cz.kocabek.animerecomedationsystem.dto;

import java.util.Map;

/**
 * Represents a user's anime list containing their ratings for different anime.
 * This record is used to store and transfer user-specific anime rating data
 * throughout the recommendation system.
 */
public record UserAnimeList(
        /**
         * The unique identifier of the user.
         */
        Long id,

        /**
         * A map containing anime ratings where:
         * - Key (Long): The unique identifier of the anime
         * - Value (Integer): The user's rating for that anime
         */
        Map<Long, Integer> animeList) {
}
