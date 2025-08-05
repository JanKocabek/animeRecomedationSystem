package cz.kocabek.animerecomedationsystem.controller;

import cz.kocabek.animerecomedationsystem.dto.FormData;
import cz.kocabek.animerecomedationsystem.service.AnimeService;
import cz.kocabek.animerecomedationsystem.service.RecommendationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {

    RecommendationService recommendationService;
    AnimeService animeService;

    public ViewController(RecommendationService recommendationService, AnimeService animeService) {
        this.recommendationService = recommendationService;
        this.animeService = animeService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("anime", new FormData());
        return "index";
    }

    @PostMapping("/submit")
    public String postHomePage(@Valid @ModelAttribute("anime") FormData data, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        data.setAnimeName(data.getAnimeName().toLowerCase());
        Long id;
        try {
            id = animeService.getAnimeIdByName(data.getAnimeName());
        } catch (Exception e) {
            bindingResult.rejectValue("animeName", "error.anime", e.getMessage());
            return "index";
        }
        redirectAttributes.addAttribute("id", id);
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String getResultPage(@RequestParam Long id, Model model) {
        final var recommendations = recommendationService.getAnimeRecommendation(id);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("anime", new FormData());
        return "result";
    }
}
