package cz.kocabek.animerecomedationsystem.service.RecommendationConfig;

public class RecommendationConfig {
    public static final int MIN_RATING_GET = 0; //min rating for the filter
    public static final int MAX_RATING_GET = 10;//max rating for the filter
    public static final int USERIDS_SIZE_Q_PAGE = 1000;//number of users id in one page
    public static final int MAX_SCORE = 10;// max available score in db
    public static final int MIN_SCORE = 0;//min available score in db
    public static final int FINAL_ANIME_LIST_SIZE = 50;
    public static final double OCCURRENCE_WEIGHT = 0.5;
    public static final double SCORE_WEIGHT = 0.5;
}
