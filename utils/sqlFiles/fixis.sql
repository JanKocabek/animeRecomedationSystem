INSERT INTO users(`Mal ID`)
SELECT DISTINCT uas.user_id FROM users_anime_score uas
WHERE uas.user_id NOT IN (select `Mal ID` from users);

INSERT INTO anime(anime_id,Name)
SELECT DISTINCT uas.anime_id, uas.`Anime Title`
FROM users_anime_score uas
         LEFT JOIN anime a ON a.`anime_id` = uas.anime_id
WHERE a.`anime_id` IS NULL