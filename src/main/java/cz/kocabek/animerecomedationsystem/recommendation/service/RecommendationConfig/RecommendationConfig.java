package cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig;

import cz.kocabek.animerecomedationsystem.recommendation.dto.InputDTO;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class RecommendationConfig {
    private String animeName;
    private Long animeId;
    private int minScore = ConfigConstant.MIN_INPUT_SCORE;
    private int maxUsers = ConfigConstant.MAX_USERS_PER_PAGE;
    private boolean onlyInAnimeGenres = false;
    private InputDTO configForm = new InputDTO(this.animeName, this.minScore, this.maxUsers, this.onlyInAnimeGenres);


    public void updateConfig(InputDTO formData) {
        this.configForm = formData;
        this.animeName = formData.getAnimeName();
        this.minScore = formData.getMinRating();
        this.maxUsers = formData.getMaxUsers();
        this.onlyInAnimeGenres = formData.isOnlyInAnimeGenres();
    }

    public void resetConfigForm() {
        this.configForm = new InputDTO(null,ConfigConstant.MIN_INPUT_SCORE,ConfigConstant.MAX_USERS_PER_PAGE, false);
    }
}

