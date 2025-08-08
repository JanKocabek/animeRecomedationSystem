package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.dto.AnimeOutDTO;
import cz.kocabek.animerecomedationsystem.dto.RecommendationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Service
@SessionScope
public class DTOResultBuilder {
    private final AnimeService animeService;
    private final RecommendationDTO resultDto;

    public DTOResultBuilder(AnimeService animeService) {
        this.animeService = animeService;
        resultDto = new RecommendationDTO(this);
    }

    public DTOResultBuilder init(String inputAnimeNames) {
        return init(List.of(inputAnimeNames));
    }

    public DTOResultBuilder init(List<String> inputAnimeNames) {
        resultDto.setInputAnimeNames(inputAnimeNames);
        return this;
    }

    public DTOResultBuilder addRecommendation(List<AnimeOutDTO> recommendedAnime) {
        resultDto.setRecommendedAnime(recommendedAnime);
        return this;
    }

    public RecommendationDTO build() {
        return resultDto;
    }

    public DTOResultBuilder populateAnimeNames(Long animeId) {
        return populateAnimeNames(List.of(animeId));
    }

    public DTOResultBuilder populateAnimeNames(List<Long> animeIds) {
        resultDto.setInputAnimeNames(animeService.getAnimeNamesByIds(animeIds).stream().toList());
        return this;
    }
}
