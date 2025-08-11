package cz.kocabek.animerecomedationsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RecommendationDTO implements Serializable {
    private List<String> inputAnimeNames;
    private List<AnimeOutDTO> recommendedAnime;
}

