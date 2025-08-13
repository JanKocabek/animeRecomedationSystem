package cz.kocabek.animerecomedationsystem.controller;

import cz.kocabek.animerecomedationsystem.dto.ConfigForm;
import cz.kocabek.animerecomedationsystem.service.AnimeService;
import cz.kocabek.animerecomedationsystem.service.DTOResultBuilder;
import cz.kocabek.animerecomedationsystem.service.RecommendationConfig.Config;
import cz.kocabek.animerecomedationsystem.service.RecommendationService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class ViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);

    RecommendationService recommendationService;
    AnimeService animeService;
    DTOResultBuilder resultBuilder;
    Config config;

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("anime", config.getConfigForm());
        return "index";
    }

    @PostMapping("/submit")
    public String postHomePage(@Valid @ModelAttribute("anime") ConfigForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "index";
        try {
            Long id = processForm(form);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/result";
        } catch (ValidationException e) {
            bindingResult.rejectValue("animeName", "error.anime", e.getMessage());
            return "index";
        }
    }

    private Long processForm(ConfigForm form) throws ValidationException {
        config.updateConfig(form);
        Long id = animeService.getAnimeIdByName(config.getAnimeName());
        resultBuilder.init(config.getAnimeName());
        return id;
    }


    @GetMapping("/result")
    public String getResultPage(@RequestParam("id") Long animeId, Model model) {
        final var recommendations = recommendationService.getAnimeRecommendation(animeId);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("anime", config.getConfigForm());
        return "result";
    }

    @PostMapping("/result/submit")
    public String postResultPage(@Valid @ModelAttribute("anime") ConfigForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        final var previousAnime = resultBuilder.getResultDto();
        if (bindingResult.hasErrors()) {
            model.addAttribute("recommendations", previousAnime);
            return "result";
        }
        try {
            Long id = processForm(form);
            resultBuilder.init(form.getAnimeName());
            redirectAttributes.addAttribute("id", id);
            return "redirect:/result";
        } catch (Exception e) {
            bindingResult.rejectValue("animeName", "error.anime", e.getMessage());
            model.addAttribute("recommendations", previousAnime);
            return "result";
        }

    }
}
