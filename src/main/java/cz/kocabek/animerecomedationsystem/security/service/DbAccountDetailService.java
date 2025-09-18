package cz.kocabek.animerecomedationsystem.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cz.kocabek.animerecomedationsystem.account.repository.AppAccRepository;

@Service
public class DbAccountDetailService implements UserDetailsService {

    AppAccRepository appAccRepository;

    public DbAccountDetailService(AppAccRepository appAccRepository) {
        this.appAccRepository = appAccRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = appAccRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .roles(user.getRole().name())
                .build();
    }
}
