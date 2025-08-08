package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.RecommendationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Service
@SessionScope
public class DTOResultBuilder {
    private final RecommendationDTO resultDto;
    private List<String> animeNames;

    public DTOResultBuilder() {
        resultDto = new RecommendationDTO();
    }

    public DTOResultBuilder init(String inputAnimeNames) {
        return init(List.of(inputAnimeNames));
    }

    public DTOResultBuilder init(List<String> inputAnimeNames) {
        resultDto.setInputAnimeNames(inputAnimeNames);
        this.animeNames = inputAnimeNames;
        return this;
    }

    public DTOResultBuilder addRecommendation(List<AnimeOutDTO> recommendedAnime) {
        resultDto.setRecommendedAnime(recommendedAnime);
        return this;
    }

    public RecommendationDTO build() {
        if (resultDto.getInputAnimeNames() == null || resultDto.getInputAnimeNames().isEmpty())
            resultDto.setInputAnimeNames(animeNames);
        return resultDto;
    }
}
