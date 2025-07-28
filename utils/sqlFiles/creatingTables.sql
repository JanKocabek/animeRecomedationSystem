-- Create genres table
CREATE TABLE genres
(
    genre_id   INT AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(50) UNIQUE
);

-- Extract unique genres from CSV data
INSERT INTO genres (genre_name)
SELECT DISTINCT TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(Genres_backup, ',', n.digit + 1), ',', -1)) AS genre_name
FROM anime
         JOIN (SELECT 0 digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) n
WHERE LENGTH(Genres_backup) - LENGTH(REPLACE(Genres_backup, ',', '')) >= n.digit
  AND TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(Genres_backup, ',', n.digit + 1), ',', -1)) != '';

-- Create junction table and populate
CREATE TABLE anime_genres
(
    anime_id INT,
    genre_id INT,
    FOREIGN KEY (anime_id) REFERENCES anime (anime_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
);

INSERT INTO anime_genres(anime_id, genre_id)
SELECT DISTINCT a.anime_id,
                g.genre_id
FROM anime a
         JOIN genres g ON FIND_IN_SET(g.genre_name, REPLACE(a.Genres_backup, ', ', ',')) > 0 #erasing space between genres so the genres act as SET
WHERE a.Genres_backup IS NOT NULL
  AND a.Genres_backup != '';
