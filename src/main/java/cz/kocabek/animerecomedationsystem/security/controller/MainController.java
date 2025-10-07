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
import cz.kocabek.animerecomedationsystem.security.dto.SettingDTO;
import cz.kocabek.animerecomedationsystem.security.service.PasswordService;
import cz.kocabek.animerecomedationsystem.security.service.RegistrationService;
import jakarta.validation.Valid;

@Controller
public class MainController {

    private final PasswordService passwordService;
    private final RegistrationService registration;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MainController.class);
    private static final String REG_ENDPOINT = "auth/registration";
    private static final String SETTING_ENDPOINT = "settings";

    public MainController(RegistrationService registration, PasswordService passwordService) {
        this.registration = registration;
        this.passwordService = passwordService;
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

    @GetMapping("/" + SETTING_ENDPOINT)
    public String getSettingPage(Model model) {
        model.addAttribute("passwordForm", new SettingDTO());
        return SETTING_ENDPOINT;
    }

    @PostMapping("/" + SETTING_ENDPOINT + "/changePassword")
    public String changePassword(@Valid @ModelAttribute("passwordForm") SettingDTO settingForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return SETTING_ENDPOINT;
        }
        if (!passwordService.checkPassword(settingForm)) {
            result.rejectValue("oldPass", "error.nonMatchingPassword", "your Current Password don't match. Try again");
            return SETTING_ENDPOINT;
        }
        passwordService.changePassword(settingForm);
        redirectAttributes.addFlashAttribute("successMessage", "Your password was changed successfully.");
        return "redirect:/";
    }

    @PostMapping("/" + SETTING_ENDPOINT + "/delete")
    public String deleteAccount() {
        registration.deleteCurrentUser();
        return "redirect:/logout";
    }

}
