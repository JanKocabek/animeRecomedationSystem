package cz.kocabek.animerecomedationsystem.user.repository;

import cz.kocabek.animerecomedationsystem.user.entity.AppAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface AppAccRepository extends JpaRepository<AppAccount, Integer> {
    Optional<AppAccount> findByUsername(String username);

    @Query("select a.id from AppAccount a where a.username = ?1")
    @NonNull
    Optional<Long> findAccountIdByUsername(@NonNull String username);
}