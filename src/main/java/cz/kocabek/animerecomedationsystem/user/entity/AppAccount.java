package cz.kocabek.animerecomedationsystem.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "app_accounts", schema = "mydatabase", uniqueConstraints = {
        @UniqueConstraint(name = "username", columnNames = {"username"})
})
public class AppAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    @NotNull
    private String passwordHash;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    @NotNull
    private Instant createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 200)
    private RoleType role;

    @OneToMany(mappedBy = "acc", fetch = FetchType.LAZY)
    private Set<AccWatchlist> watchlist = new HashSet<>();

    public AppAccount(String username, String passwordHash, RoleType role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public AppAccount() {
    }

    @PrePersist
    public void setCreatedAt() {
        if (this.createdAt == null)
            this.createdAt = Instant.now();
    }
}