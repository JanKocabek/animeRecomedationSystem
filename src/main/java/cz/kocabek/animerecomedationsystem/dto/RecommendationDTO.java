package cz.kocabek.animerecomedationsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationDTO {
    List<String> inputAnimeNames;
    List<AnimeOutDTO> recommendedAnime;
}

