package cz.kocabek.animerecomedationsystem.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.kocabek.animerecomedationsystem.account.UserSessionData;
import cz.kocabek.animerecomedationsystem.account.entity.AppAccount;
import cz.kocabek.animerecomedationsystem.account.repository.AppAccRepository;
import cz.kocabek.animerecomedationsystem.security.dto.RegistrationDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final AppAccRepository appAccRepository;
    private final UserSessionData userSessionData;

    public boolean registerNewUser(RegistrationDTO registrationDTO) {
        boolean isUsernameOk = appAccRepository.findByUsername(registrationDTO.username()).isEmpty();
        if (isUsernameOk) {
            appAccRepository.save(createAppAccount(registrationDTO));
            return true;
        }
        return false;
    }

    private AppAccount createAppAccount(RegistrationDTO registrationDTO) {
        return new AppAccount(
                registrationDTO.username(),
                passwordEncoder.encode(registrationDTO.password()),
                registrationDTO.role());
    }

    @Transactional
    public void deleteCurrentUser() {
        if (userSessionData.getUserId() != null) {
            appAccRepository.deleteById(userSessionData.getUserId());
        } else {
            throw new IllegalStateException("No user is currently logged in.");
        }
    }

}
