package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.RecommendationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTOResultBuilder {

    private RecommendationDTO resultDto = null;

    public void init(String inputAnimeNames) {
        set();
        resultDto.setInputAnimeNames(List.of(inputAnimeNames));
    }

    public void init(List<String> inputAnimeNames) {
       set();
        resultDto.setInputAnimeNames(inputAnimeNames);
    }

    private void set (){
        if (resultDto == null) resultDto = new RecommendationDTO();
    }

    public DTOResultBuilder addRecommendation(List<AnimeOutDTO> recommendedAnime) {
        resultDto.setRecommendedAnime(recommendedAnime);
        return this;
    }

    public RecommendationDTO build() {
        return resultDto;
    }

}
