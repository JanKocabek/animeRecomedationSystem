-- Create Anime table--
CREATE TABLE
  anime (
    anime_id INT UNSIGNED NOT NULL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    `English name` VARCHAR(255),
    Score DOUBLE,
    Type TEXT,
    Episodes BIGINT,
    Status TEXT,
    Source TEXT,
    Rating TEXT,
    Rank INT,
    Popularity INT,
    `Image URL` TEXT
  );

-- Create genres table
CREATE TABLE
  genres (
    genre_id INT AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(50) UNIQUE
  );

-- Extract unique genres from CSV data
INSERT INTO
  genres (genre_name)
SELECT DISTINCT
  TRIM(
    SUBSTRING_INDEX (
      SUBSTRING_INDEX (Genres, ',', n.digit + 1),
      ',',
      -1
    )
  ) AS genre_name
FROM
  anime
  JOIN (
    SELECT
      0 digit
    UNION ALL
    SELECT
      1
    UNION ALL
    SELECT
      2
    UNION ALL
    SELECT
      3
    UNION ALL
    SELECT
      4
  ) n
WHERE
  LENGTH (Genres) - LENGTH (REPLACE (Genres, ',', '')) >= n.digit
  AND TRIM(
    SUBSTRING_INDEX (
      SUBSTRING_INDEX (Genres, ',', n.digit + 1),
      ',',
      -1
    )
  ) != '';

-- Create junction table and populate
CREATE TABLE
  anime_genres (
    anime_id INT,
    genre_id INT,
    FOREIGN KEY (anime_id) REFERENCES anime (anime_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
  );

INSERT INTO
  anime_genres (anime_id, genre_id)
SELECT DISTINCT
  a.anime_id,
  g.genre_id
FROM
  anime a
  JOIN genres g ON FIND_IN_SET (
    g.genre_name,
    REPLACE (a.Genres, ', ', ',')
  ) > 0
  
WHERE
  a.Genres IS NOT NULL
  AND a.Genres != '';