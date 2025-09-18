package cz.kocabek.animerecomedationsystem.recommendation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.kocabek.animerecomedationsystem.account.service.AccService;
import cz.kocabek.animerecomedationsystem.account.service.WatchListService;

@Controller
class WatchlistBtnController {


    WatchListService watchListService;
    AccService accService;

    private static final String WATCH_BTN_FRAGMENT = "fragments/watchBtn ::";
    private static final String ANIMEID_MODEL_ATR = "animeId";

    public WatchlistBtnController(WatchListService watchListService, AccService accService) {
        this.watchListService = watchListService;
        this.accService = accService;
    }


    @PostMapping("/add_watch")
    public String postWatchlistPage(@RequestParam long animeId, Model model) {
        accService.addAnimeToWatchlist(animeId);
        model.addAttribute(ANIMEID_MODEL_ATR, animeId);
        return WATCH_BTN_FRAGMENT + " watchBtnRemove";
    }

    @PatchMapping("/remove_watch")
    public String removeFromWatchList(@RequestParam long animeId, Model model) {
        watchListService.removeFromWatchlist(animeId);
        model.addAttribute(ANIMEID_MODEL_ATR, animeId);
        return WATCH_BTN_FRAGMENT + " watchBtnReAdd";
    }

    @PatchMapping("/readd_watch")
    public String reAddToWatchList(@RequestParam long animeId, Model model) {
        watchListService.reAddToWatchlist(animeId);
        model.addAttribute(ANIMEID_MODEL_ATR, animeId);
        return WATCH_BTN_FRAGMENT + " watchBtnRemove";
    }

}
