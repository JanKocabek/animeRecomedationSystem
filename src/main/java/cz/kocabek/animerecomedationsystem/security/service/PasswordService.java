package cz.kocabek.animerecomedationsystem.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.annotation.Transactional;

import cz.kocabek.animerecomedationsystem.account.UserSessionData;
import cz.kocabek.animerecomedationsystem.account.repository.AppAccRepository;
import cz.kocabek.animerecomedationsystem.security.dto.SettingDTO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordService {

    private final AppAccRepository appAccRepository;
    private final UserSessionData userSessionData;
    private final PasswordEncoder passwordEncoder;

    public boolean checkPassword(SettingDTO setting) {
        final var oldPass = appAccRepository.findAccountPasswordById(userSessionData.getUserId());
        return passwordEncoder.matches(setting.oldPass(), oldPass);
    }

    @Transactional
    public void changePassword(SettingDTO setting) {
        final var newHash = passwordEncoder.encode(setting.newPass());
        final var rows = appAccRepository.updatePassword(userSessionData.getUserId(), newHash);
        if (rows != 1) {
            throw new NoTransactionException(
                "Failed to update password for userId: " + userSessionData.getUserId() +
                ". Expected 1 row to be updated, but got: " + rows
            );
        }
    }
}
