package cz.kocabek.animerecomedationsystem.security.controller;


import cz.kocabek.animerecomedationsystem.security.dto.RegistrationDTO;
import cz.kocabek.animerecomedationsystem.security.service.RegistrationService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MainController.class);
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
        RegistrationDTO account = new RegistrationDTO();
        model.addAttribute("account", account);
        return "auth/registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") RegistrationDTO acc, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/registration";
        }
        try {
            registration.registerNewUser(acc);
        } catch (ValidationException e) {
            LOGGER.error("Validation Error during registration: {}", e.getMessage());
            result.rejectValue("username", "error.detail", e.getMessage());
            return "auth/registration";
        } catch
        (Exception e) {
            LOGGER.error("Error during registration: {}", e.getMessage());
            return "/error";
        }
        return "auth/index";
    }
}
