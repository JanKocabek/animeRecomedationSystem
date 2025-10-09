package cz.kocabek.animerecomedationsystem.security.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cz.kocabek.animerecomedationsystem.security.dto.RegistrationDTO;
import cz.kocabek.animerecomedationsystem.security.service.RegistrationService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

    private final RegistrationService registration;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);
    private static final String REG_ENDPOINT = "auth/registration";

    public AuthenticationController(RegistrationService registration) {
        this.registration = registration;
    }

    @GetMapping("/")
    public String getSignPage() {
        return "auth/index";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        RegistrationDTO account = new RegistrationDTO();
        model.addAttribute("account", account);
        return REG_ENDPOINT;
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") RegistrationDTO acc, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return REG_ENDPOINT;
        }
        if (!registration.registerNewUser(acc)) {
            result.rejectValue("username", "error.detail", "Username already exists");
            return REG_ENDPOINT;
        }
        redirectAttributes.addFlashAttribute("successMessage", "Your account was created successfully. You can now sign in.");
        return "redirect:/";
    }
}
