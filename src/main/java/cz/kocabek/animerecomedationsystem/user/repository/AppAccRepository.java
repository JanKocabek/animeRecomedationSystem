package cz.kocabek.animerecomedationsystem.user.repository;

import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppAccRepository extends JpaRepository<AppAccount, Integer> {
    Optional<AppAccount> findByUsername(String username);
}