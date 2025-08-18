package cz.kocabek.animerecomedationsystem.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getSignPage() {
        return "index";
    }
}
