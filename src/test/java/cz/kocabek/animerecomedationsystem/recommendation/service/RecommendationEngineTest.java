package cz.kocabek.animerecomedationsystem.recommendation.service;



import cz.kocabek.animerecomedationsystem.recommendation.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.recommendation.dto.UserAnimeList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationEngineTest {
    RecommendationEngine engine;

    @BeforeEach
    void setUp() {
        engine = new RecommendationEngine();
    }

    @Test
    void testCountAnimeOccurrences_ShouldReturnCorrectAnimeData_WhenUserAnimeListProvided() {
        //arrange
        List<UserAnimeList> list = List.of(new UserAnimeList(1L, new HashMap<>() {{
            put(1L, 10);
            put(2L, 10);
            put(3L, 10);
        }}));
        //act
        Map<Long, AnimeOutDTO> out = engine.buildAnimeOccurrencesMap(list);
        //assert
        assertNotNull(out);
        assertEquals(list.getFirst().animeList().size(), out.size());
        assertEquals(1, out.get(2L).getOccurrences());
        assertEquals(10, out.get(2L).getSumOfRatings());
    }

    @Test
    void testShouldReturnRightOccurrences() {
        //arrange
        List<UserAnimeList> list = List.of(new UserAnimeList(1L, new HashMap<>() {{
            put(1L, 10);
        }}), new UserAnimeList(2L, new HashMap<>() {{
            put(1L, 10);
        }}));
        //act
        Map<Long, AnimeOutDTO> out = engine.buildAnimeOccurrencesMap(list);
        //assert
        assertNotNull(out);
        assertEquals(1, out.size());
        assertEquals(2, out.get(1L).getOccurrences());
        assertEquals(20, out.get(1L).getSumOfRatings());
    }

    @Test
    void testMultipleUsersMultipleAnime() {
        //arrange
        List<UserAnimeList> list = List.of(new UserAnimeList(1L, new HashMap<>() {{
            put(1L, 10);
            put(2L, 8);
        }}), new UserAnimeList(2L, new HashMap<>() {{
            put(1L, 10);
            put(3L, 7);
        }}), new UserAnimeList(3L, new HashMap<>() {{
            put(1L, 10);
            put(2L, 10);
            put(4L, 6);
        }}));
        //act
        Map<Long, AnimeOutDTO> out = engine.buildAnimeOccurrencesMap(list);
        //assert
        assertNotNull(out);
        assertEquals(4, out.size());
        assertEquals(3, out.get(1L).getOccurrences());
        assertEquals(30, out.get(1L).getSumOfRatings());
        assertEquals(2, out.get(2L).getOccurrences());
        assertEquals(18, out.get(2L).getSumOfRatings());

    }

    @Test
    void buildAnimeOccurrencesMap() {
        //arrange
        final var list = List.of(new UserAnimeList(1L, new HashMap<>() {{
            put(1L, 10);
            put(2L, 8);
            put(8L, 1);
        }}), new UserAnimeList(2L, new HashMap<>() {{
            put(1L, 10);
            put(3L, 7);
            put(8L, 1);
        }}), new UserAnimeList(3L, new HashMap<>() {{
            put(1L, 10);
            put(2L, 10);
            put(4L, 4);
            put(5L, 5);
            put(8L, 1);
        }}), new UserAnimeList(4L, new HashMap<>() {{
            put(8L, 1);
        }}));
        //act
        final var map = engine.buildAnimeOccurrencesMap(list);
        List<Map.Entry<Long, AnimeOutDTO>> entries = new ArrayList<>(map.entrySet());
        //assert
        assertThat(map).hasSize(6);
        assertThat(map.get(1L).getOccurrences()).isEqualTo(3);
        assertThat(map.entrySet()).extracting(Map.Entry::getKey).containsExactlyElementsOf(List.of(8L, 1L, 2L, 3L, 4L, 5L));
        // Verify the entries are sorted by occurrences (highest first)
        for (int i = 0; i < entries.size() - 1; i++) {
            assertThat(entries.get(i).getValue().getOccurrences()).isGreaterThanOrEqualTo(entries.get(i + 1).getValue().getOccurrences());
        }
    }

    @Test
    void weightAnime() {
        //arrange
        final var list = List.of(new UserAnimeList(1L, new HashMap<>() {{
                    put(1L, 10);
                    put(2L, 8);
                    put(8L, 1);
                }}), new UserAnimeList(2L, new HashMap<>() {{
                    put(1L, 10);
                    put(3L, 7);
                    put(8L, 1);
                }}), new UserAnimeList(3L, new HashMap<>() {{
                    put(1L, 10);
                    put(2L, 10);
                    put(4L, 4);
                    put(5L, 5);
                }})
        );
        //act
       final var map= engine.buildAnimeOccurrencesMap(list);
       final var weightedAnime = engine.weightAnime(map, AnimeScore.compositeScoring);
       //assert
        assertThat(weightedAnime).hasSize(6);
        assertThat(weightedAnime.keySet()).containsExactly(1L, 2L, 3L, 5L, 4L,8L);//error it give  prefeer more occurce then rating

    }
}