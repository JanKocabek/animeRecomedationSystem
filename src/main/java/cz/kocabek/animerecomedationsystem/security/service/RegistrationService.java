package cz.kocabek.animerecomedationsystem.security.service;

import cz.kocabek.animerecomedationsystem.security.dto.RegistrationDTO;
import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import cz.kocabek.animerecomedationsystem.user.repository.AppAccRepository;
import jakarta.validation.ValidationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    AppAccRepository appAccRepository;

    public RegistrationService(AppAccRepository appAccRepository) {
        this.appAccRepository = appAccRepository;
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

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
                bCryptPasswordEncoder.encode(registrationDTO.password()),
                registrationDTO.role());
    }
}
