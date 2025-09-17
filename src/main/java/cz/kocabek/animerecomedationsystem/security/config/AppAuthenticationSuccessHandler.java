package cz.kocabek.animerecomedationsystem.security.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import cz.kocabek.animerecomedationsystem.account.UserSessionData;
import cz.kocabek.animerecomedationsystem.account.service.AccService;

import java.io.IOException;

@Component
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    AccService accService;
    UserSessionData userSessionData;

    public AppAuthenticationSuccessHandler(AccService accService, UserSessionData userSessionData) {
        this.accService = accService;
        this.userSessionData = userSessionData;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final var username = authentication.getName();
        final var userId = accService.getUserIdByUsername(username);
        userSessionData.setUserId(userId);
        response.sendRedirect("/main");
    }
}
