package cz.kocabek.animerecomedationsystem.recommendation.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class RecommendationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    private List<String> inputAnimeNames;
    private List<AnimeOutDTO> recommendedAnime;
}

