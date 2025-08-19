package cz.kocabek.animerecomedationsystem.user.repository;

import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AppAccRepository extends Repository<AppAccount, Integer> {
    Optional<AppAccount> findByUsername(String username);
}