package cz.kocabek.animerecomedationsystem.security.dto;

import cz.kocabek.animerecomedationsystem.user.entity.RoleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

import java.time.Instant;

@Value
public class RegistrationDTO {
    @NotEmpty(message = "Username cannot be empty!")
    @Pattern(regexp = "^[a-zA-Z0-9_]$", message = """
            Username must be at least 5 character long.
            It can contain only letters, numbers and underscore.
            """)
    String username;
    @NotEmpty(message = "Password cannot be empty!")
    @Min(8)
    String password;
    RoleType role;
    Instant createdAt;

    public RegistrationDTO(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = RoleType.USER;
        this.createdAt = Instant.now();
    }
}
