package cz.kocabek.animerecomedationsystem.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cz.kocabek.animerecomedationsystem.security.dto.DeletingCheckDTO;
import cz.kocabek.animerecomedationsystem.security.dto.SettingDTO;
import cz.kocabek.animerecomedationsystem.security.service.PasswordService;
import cz.kocabek.animerecomedationsystem.security.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/settings")
public class SettingPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SettingPageController.class);
    private static final String SETTING_PAGE = "settings";
    private static final String SETTING_ENDPOINT = "/settings";
    private static final String INDEX_ENDPOINT = "/";
    private static final String CHANGEPASS_ENDPOINT = "/changePassword";
    private static final String DELETEACC_ENDPOINT = "/delete";
    private static final String MAIN_ENDPOINT = "/main";
    private final PasswordService passwordService;
    private final RegistrationService registration;

    @GetMapping
    public String getSettingPage(Model model) {
        model.addAttribute("passwordForm", new SettingDTO());
        model.addAttribute("deletingCheck", new DeletingCheckDTO());
        model.addAttribute("changePasswordPoint", SETTING_ENDPOINT + CHANGEPASS_ENDPOINT);
        model.addAttribute("deleteAccountPoint", SETTING_ENDPOINT + DELETEACC_ENDPOINT);
        model.addAttribute("mainPoint", MAIN_ENDPOINT);
        return SETTING_PAGE;
    }

    @PostMapping(CHANGEPASS_ENDPOINT)
    public String changePassword(@Valid @ModelAttribute("passwordForm") SettingDTO settingForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("deletingCheck", new DeletingCheckDTO());
        model.addAttribute("changePasswordPoint", SETTING_ENDPOINT + CHANGEPASS_ENDPOINT);
        model.addAttribute("deleteAccountPoint", SETTING_ENDPOINT + DELETEACC_ENDPOINT);
        model.addAttribute("mainPoint", MAIN_ENDPOINT);
        if (result.hasErrors()) {
            return SETTING_PAGE;
        }
        if (!passwordService.checkPassword(settingForm.oldPass())) {
            result.rejectValue("oldPass", "error.nonMatchingPassword", "your Current Password don't match. Try again");
            return SETTING_PAGE;
        }
        passwordService.changePassword(settingForm);
        redirectAttributes.addFlashAttribute("successMessage", "Your password was changed successfully.");
        return "redirect:" + INDEX_ENDPOINT;
    }

    @PostMapping(DELETEACC_ENDPOINT)
    public String checkPassword(@Valid @ModelAttribute("deletingCheck") DeletingCheckDTO deletingCheckDTO, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            return "fragments/errorDiv";
        }
        if (!passwordService.checkPassword(deletingCheckDTO.password())) {
            result.rejectValue("password", "error.nonMatchingPassword", "you put the wrong password.");
            return "fragments/errorDiv";
        }
        try {
            registration.deleteCurrentUser();
            request.getSession().invalidate();
            return "redirect:/logout";
        } catch (IllegalStateException e) {
            LOGGER.error("Error deleting user account: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Account deletion failed. Please try again.");
            return SETTING_PAGE;
        }
    }
}
