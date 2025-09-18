#important indexes for faster quieries

#for looking onfor anime name user iput
CREATE INDEX idx_anime_name_ci ON anime (`name`);
CREATE INDEX idx_anime_english_name_ci ON anime (`english name`);
#optimizing for looking for score from higher to lower score
CREATE INDEX idx_anime_score_desc ON anime (Score DESC);

CREATE INDEX idx_users_anime_score_anime_rating_range ON users_anime_score (anime_id, rating, user_id);
CREATE INDEX idx_critical_user_exclude_anime ON users_anime_score (user_id, anime_id, rating);

CREATE INDEX idx_users_anime_score_anime_rating ON users_anime_score (anime_id, rating);
CREATE INDEX idx_users_anime_score_user_anime ON users_anime_score (user_id, anime_id);
CREATE INDEX idx_users_anime_score_user_rating ON users_anime_score (user_id, rating DESC);
#index for slowest and main query
CREATE INDEX idx_optimized_user_rating_fetch ON users_anime_score (user_id, rating DESC, anime_id);

CREATE INDEX idx_anime_genres_anime_id ON anime_genres(anime_id);