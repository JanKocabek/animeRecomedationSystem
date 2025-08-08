package cz.kocabek.animerecomedationsystem.dto;

import cz.kocabek.animerecomedationsystem.service.DTOResultBuilder;
import lombok.Data;

import java.util.List;

@Data

public class RecommendationDTO {
    private final DTOResultBuilder builder;

    private List<String> inputAnimeNames;
    private List<AnimeOutDTO> recommendedAnime;

    public RecommendationDTO(DTOResultBuilder builder) {
        this.builder = builder;
    }

}

