package cz.kocabek.animerecomedationsystem.security.service;

import cz.kocabek.animerecomedationsystem.security.dto.RegistrationDTO;
import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import cz.kocabek.animerecomedationsystem.user.repository.AppAccRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    AppAccRepository appAccRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public RegistrationService(AppAccRepository appAccRepository) {
        this.appAccRepository = appAccRepository;
    }

    public void registerNewUser(RegistrationDTO registrationDTO) {
        appAccRepository.findByUsername(registrationDTO.getUsername()).ifPresent(_ -> {
            throw new IllegalArgumentException("Username is already taken!");
        });
        appAccRepository.save(createAppAccount(registrationDTO));
    }

    private AppAccount createAppAccount(RegistrationDTO registrationDTO) {
        final var appAccount = new AppAccount();
        appAccount.setUsername(registrationDTO.getUsername());
        appAccount.setPasswordHash(bCryptPasswordEncoder.encode(registrationDTO.getPassword()));
        appAccount.setCreatedAt(registrationDTO.getCreatedAt());
        appAccount.setRole(registrationDTO.getRole());
        return appAccount;
    }

}
