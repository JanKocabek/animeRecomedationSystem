package cz.kocabek.animerecomedationsystem.service.RecommendationConfig;

import cz.kocabek.animerecomedationsystem.dto.ConfigForm;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class Config {
    private String animeName = "";
    private int minRating;
    private int maxUsers;
    private ConfigForm configForm = new ConfigForm();


    public void updateConfig(ConfigForm formData) {
        this.configForm = formData;
        this.animeName = formData.getAnimeName();
        this.minRating = formData.getMinRating();
        this.maxUsers = formData.getMaxUsers();
    }

    public void resetConfigForm() {
        this.configForm = new ConfigForm();
    }
}

