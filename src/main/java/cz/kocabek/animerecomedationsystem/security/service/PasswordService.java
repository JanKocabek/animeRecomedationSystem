package cz.kocabek.animerecomedationsystem.security.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cz.kocabek.animerecomedationsystem.account.UserSessionData;
import cz.kocabek.animerecomedationsystem.account.repository.AppAccRepository;
import cz.kocabek.animerecomedationsystem.security.dto.SettingDTO;
import jakarta.persistence.EntityManager;

@Service
public class PasswordService {

    private final AppAccRepository appAccRepository;
    private final UserSessionData userSessionData;
    private final BCryptPasswordEncoder encoder;

    PasswordService(AppAccRepository appAccRepository, UserSessionData userSessionData, EntityManager entityManager) {
        this.appAccRepository = appAccRepository;
        this.userSessionData = userSessionData;
        encoder = new BCryptPasswordEncoder();
    }

    public boolean checkPassword(SettingDTO setting) {
        final var oldPass = appAccRepository.findAccountPasswordById(userSessionData.getUserId());
        if (!encoder.matches(setting.oldPass(), oldPass)) {
            return false;
        }
        final var newHash = encoder.encode(setting.newPass());
        appAccRepository.updatePassword(userSessionData.getUserId(), newHash);
        return true;
    }
}
