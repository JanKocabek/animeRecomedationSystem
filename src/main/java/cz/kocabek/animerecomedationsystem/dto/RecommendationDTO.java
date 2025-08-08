package cz.kocabek.animerecomedationsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationDTO {
    private List<String> inputAnimeNames;
    private List<AnimeOutDTO> recommendedAnime;
}

