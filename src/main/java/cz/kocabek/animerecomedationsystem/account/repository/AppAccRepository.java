package cz.kocabek.animerecomedationsystem.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import cz.kocabek.animerecomedationsystem.account.entity.AppAccount;

public interface AppAccRepository extends JpaRepository<AppAccount, Integer> {
    Optional<AppAccount> findByUsername(String username);

    @Query("select a.id from AppAccount a where a.username = ?1")
    @NonNull
    Optional<Integer> findAccountIdByUsername(@NonNull String username);

}