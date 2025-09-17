package cz.kocabek.animerecomedationsystem.security.dto;

import cz.kocabek.animerecomedationsystem.account.entity.RoleType;
import cz.kocabek.animerecomedationsystem.security.validation.Password;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegistrationDTO(
        @Size(min = 5, message = "User Name must be at least 5 character long") @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "User Name can contain only letters, numbers and underscore.") String username,
        @Password String password,
        RoleType role) {
    public RegistrationDTO() {
        this("", "", RoleType.USER);
    }
}
