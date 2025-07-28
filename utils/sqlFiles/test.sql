-- Check if population worked
SELECT COUNT(*) FROM anime_genres;

-- See some examples
SELECT a.Name, GROUP_CONCAT(g.genre_name) as genres
FROM anime a
         JOIN anime_genres ag ON a.anime_id = ag.anime_id
         JOIN genres g ON ag.genre_id = g.genre_id
GROUP BY a.anime_id, a.Name
LIMIT 10;

-- Compare with original (should match)
SELECT Name, Genres_backup FROM anime LIMIT 10;