package cz.kocabek.animerecomedationsystem.recommendation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cz.kocabek.animerecomedationsystem.recommendation.dto.InputDTO;
import cz.kocabek.animerecomedationsystem.recommendation.service.DTOResultBuilder;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationService;
import cz.kocabek.animerecomedationsystem.recommendation.service.RecommendationConfig.RecommendationConfig;
import cz.kocabek.animerecomedationsystem.recommendation.service.db.AnimeService;
import cz.kocabek.animerecomedationsystem.user.service.AccService;
import cz.kocabek.animerecomedationsystem.user.service.WatchListService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
    AccService accService;
    RecommendationService recommendationService;
    AnimeService animeService;
    DTOResultBuilder resultBuilder;
    RecommendationConfig config;
    WatchListService watchListService;

    private static final String INPUT_ATR_NAME = "anime";
    private static final String ATR_ACTION = "action";

    @GetMapping("/main")
    public String getHomePage(Model model) {
        model.addAttribute(INPUT_ATR_NAME, config.getConfigForm());
        model.addAttribute(ATR_ACTION, "/submit");
        return "main";
    }

    @PostMapping("/submit")
    public String postHomePage(@Valid @ModelAttribute(INPUT_ATR_NAME) InputDTO form, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "main";
        try {
            Long id = processForm(form);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/result";
        } catch (ValidationException e) {
            bindingResult.rejectValue("animeName", "error.detail", e.getMessage());
            return "main";
        }
    }

    @GetMapping("/result")
    public String getResultPage(@RequestParam("id") Long animeId, Model model, Authentication auth) {
        try {
            checkAnimeId(animeId);
        } catch (ValidationException e) {
            // TODO: add proper return to the view by
            return "redirect:/";
        }
        final var recommendations = recommendationService.getAnimeRecommendation(animeId);
        if (auth != null) {
            watchListService.setWatchlistButtons(recommendations);
        }
        model.addAttribute("recommendations", recommendations);
        model.addAttribute(INPUT_ATR_NAME, config.getConfigForm());
        model.addAttribute(ATR_ACTION, "/result/submit");
        return "result";
    }

    @PostMapping("/result/submit")
    public String postResultPage(@Valid @ModelAttribute("anime") InputDTO form, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        // todo:move this line in the if statement
        final var previousAnime = resultBuilder.getResultDto();
        if (bindingResult.hasErrors()) {
            model.addAttribute("recommendations", previousAnime);
            return "result";
        }
        try {
            Long id = processForm(form);
            redirectAttributes.addAttribute("id", id);
            return "redirect:/result";
        } catch (Exception e) {
            bindingResult.rejectValue("animeName", "error.detail", e.getMessage());
            model.addAttribute("recommendations", previousAnime);
            return "result";
        }
    }

    @GetMapping("/anime/{id}")
    public String getAnimePage(@PathVariable Long id, Model model) {
        final var detail = animeService.getAnimeById(id);
        model.addAttribute("detail", detail);
        model.addAttribute(INPUT_ATR_NAME, config.getConfigForm());
        return "detail";
    }

    @GetMapping("/watchlist")
    public String getWatchlistPage(Model model) {
        model.addAttribute(INPUT_ATR_NAME, config.getConfigForm());
        try {
            model.addAttribute("watchlist", accService.getWatchlistData());
        } catch (IllegalStateException e) {
            return "/logout";
        }
        model.addAttribute(ATR_ACTION, "/submit");
        return "watchlist";
    }

    @DeleteMapping("/remove_uiitem")
    @ResponseBody
    public ResponseEntity<String> removeFromWatchListPage(@RequestParam long animeId) {
        try {
            watchListService.removeFromWatchlist(animeId);
        } catch (IllegalStateException e) {
            LOGGER.error("during removing item from UI happened error:%n {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
        return ResponseEntity.ok("");
    }

    private Long processForm(InputDTO form) throws ValidationException {
        config.updateConfig(form);
        Long id = animeService.getAnimeIdByName(config.getAnimeName());
        config.setAnimeId(id);
        resultBuilder.init(config.getAnimeName());
        return id;
    }

    private void checkAnimeId(Long id) throws ValidationException {
        if (config.getAnimeId() != null && config.getAnimeId().equals(id))
            return;
        final var name = animeService.getAnimeNameById(id);
        config.setAnimeName(name);
        config.setAnimeId(id);
        resultBuilder.init(name);
    }
}
