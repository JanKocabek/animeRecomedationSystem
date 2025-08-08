package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.RecommendationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTOResultBuilder {

    AnimeService animeService;
    private final RecommendationDTO resultDto = new RecommendationDTO(this);

    public DTOResultBuilder(AnimeService animeService) {
        this.animeService = animeService;
    }


    public void init(String inputAnimeNames) {
        init(List.of(inputAnimeNames));
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

    public void populateAnimeNames(Long animeId) {
        populateAnimeNames(List.of(animeId));
    }

    public void populateAnimeNames(List<Long> animeIds) {

        resultDto.setInputAnimeNames(animeService.getAnimeNamesByIds(animeIds).stream().toList());
    }

}
