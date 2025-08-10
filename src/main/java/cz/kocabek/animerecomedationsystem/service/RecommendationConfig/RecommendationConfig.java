package cz.kocabek.animerecomedationsystem.service.RecommendationConfig;

public class RecommendationConfig {
    public static final int LOWEST_ACCEPTABLE_RATING = 0; //min rating for the filter
    public static final int MAX_ACCEPTABLE_RATING = 10;//max rating for the filter
    public static final int MAX_USERS_PER_PAGE = 2000;//number of users id fetch in one page
    public static final int MAX_SCORE = 10;// max available score in db
    public static final int MIN_SCORE = 0;//min available score in db
    public static final int FINAL_ANIME_LIST_SIZE = 50;//final size of anime list for the recommendation in the UI
    public static final double OCCURRENCE_WEIGHT = 0.7; //weight of occurrence value in the recommendation algorithm
    public static final double SCORE_WEIGHT = 0.7;//weight of average score value in the recommendation algorithm
    public static final int MIN_INPUT_SCORE = 8;//min rating of input anime from the users in db for the recommendation
    public static final int MAX_INPUT_SCORE = 10;//max rating of input anime from the users in db for the recommendation
}
