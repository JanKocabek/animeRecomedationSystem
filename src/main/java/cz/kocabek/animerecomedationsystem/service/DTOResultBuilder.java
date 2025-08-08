package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.RecommendationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTOResultBuilder {

    private final RecommendationDTO resultDto = new RecommendationDTO();

    public void init(String inputAnimeNames) {
        resultDto.setInputAnimeNames(List.of(inputAnimeNames));
    }

    public void init(List<String> inputAnimeNames) {
        resultDto.setInputAnimeNames(inputAnimeNames);
    }


    public DTOResultBuilder addRecommendation(List<AnimeOutDTO> recommendedAnime) {
        resultDto.setRecommendedAnime(recommendedAnime);
        return this;
    }

    public RecommendationDTO build() {
        return resultDto;
    }

}
