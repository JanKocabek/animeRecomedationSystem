package cz.kocabek.animerecomedationsystem.security.controller;


import cz.kocabek.animerecomedationsystem.security.dto.RegistrationDTO;
import cz.kocabek.animerecomedationsystem.security.service.RegistrationService;
import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    RegistrationService registration;

    public MainController(RegistrationService registration) {
        this.registration = registration;
    }

    @GetMapping("/")
    public String getSignPage() {
        return "auth/index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        AppAccount account = new AppAccount();
        model.addAttribute("account",account);
        return "auth/registration";
    }

    @PostMapping("/SignUp")
    public String register(@ModelAttribute("user") RegistrationDTO user, Model model) {
        registration.registerNewUser(user);
        model.addAttribute("success", "User registered successfully");
        return "auth/index";
    }

}
