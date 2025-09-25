package cz.kocabek.animerecomedationsystem.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import cz.kocabek.animerecomedationsystem.account.entity.AppAccount;

public interface AppAccRepository extends JpaRepository<AppAccount, Integer> {

    Optional<AppAccount> findByUsername(String username);

    @Query("select a.id from AppAccount a where a.username = ?1")
    @NonNull
    Optional<Integer> findAccountIdByUsername(@NonNull String username);

    @Query("select a.passwordHash from AppAccount a where a.id =?1 ")
    String findAccountPasswordById(@NonNull Integer id);

    @Modifying
    @Transactional
    @Query("""
        UPDATE AppAccount a
        SET a.passwordHash = :password
        WHERE a.id = :id
            """)
    int updatePassword(@NonNull @Param("id") Integer id, @Param("password") String pass);

}
