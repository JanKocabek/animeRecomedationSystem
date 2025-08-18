package cz.kocabek.animerecomedationsystem.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/sign")
    public String getSignPage(Model model) {
        return "index";
    }
}
