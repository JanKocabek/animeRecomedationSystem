package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.UserAnimeList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationEngineTest {

    RecommendationEngine engine;

    @BeforeEach
    void setUp() {
        engine = new RecommendationEngine();
    }

    @Test
    void testCountAnimeOccurrences_ShouldReturnCorrectAnimeData_WhenUserAnimeListProvided() {
        List<UserAnimeList> list = List.of(
                new UserAnimeList(1L, new HashMap<>() {{
                    put(1L, 10);
                    put(2L, 10);
                    put(3L, 10);
                }})
        );
        Map<Long, AnimeOutDTO> out = engine.countAnimeOccurrences(list);
        assertNotNull(out);
        assertEquals(list.getFirst().animeList().size(), out.size());
        assertEquals(1, out.get(2L).getOccurrences());
        assertEquals(10, out.get(2L).getSumOfRatings());
        assertEquals(10, out.get(2L).getSumOfRatings());
        assertNull(out.get(1L).getAverageRating());
    }

    @Test
    void testShouldReturnRightOccurrences() {
        //arrange
        List<UserAnimeList> list = List.of(
                new UserAnimeList(1L, new HashMap<>() {{
                    put(1L, 10);
                }})
                , new UserAnimeList(2L, new HashMap<>() {{
                    put(1L, 10);
                }})
        );
        //act
        Map<Long, AnimeOutDTO> out = engine.countAnimeOccurrences(list);
        //assert
        assertNotNull(out);
        assertEquals(list.size(), out.size());
        assertEquals(2, out.get(1L).getOccurrences());
        assertEquals(20, out.get(1L).getSumOfRatings());
    }

    @Test
    void testMultipleUsersMultipleAnime() {
        //arrange
        List<UserAnimeList> list = List.of(
                new UserAnimeList(1L, new HashMap<>() {{
                    put(1L, 10);
                    put(2L, 8);
                }}),
                new UserAnimeList(2L, new HashMap<>() {{
                    put(1L, 10);
                    put(3L, 7);
                }}),
                new UserAnimeList(3L, new HashMap<>() {{
                    put(1L, 10);
                    put(2L, 10);
                    put(4L, 6);
                }}));
        //act
        Map<Long, AnimeOutDTO> out = engine.countAnimeOccurrences(list);
        //assert
        assertNotNull(out);
        assertEquals(4, out.size());
        assertEquals(3, out.get(1L).getOccurrences());
        assertEquals(30, out.get(1L).getSumOfRatings());
        assertEquals(2, out.get(2L).getOccurrences());
        assertEquals(18, out.get(2L).getSumOfRatings());

    }
}