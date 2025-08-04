package cz.kocabek.animerecomedationsystem.controller;

import cz.kocabek.animerecomedationsystem.dto.FormData;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("anime", new FormData());
        return "index";
    }
    @PostMapping("/submit")
    public String postHomePage(@Valid @ModelAttribute("anime") FormData data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        return "index";
    }
}
