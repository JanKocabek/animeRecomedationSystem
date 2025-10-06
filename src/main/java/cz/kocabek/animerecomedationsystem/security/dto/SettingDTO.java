package cz.kocabek.animerecomedationsystem.security.dto;

import cz.kocabek.animerecomedationsystem.security.validation.Password;
import jakarta.validation.constraints.NotBlank;

public record SettingDTO(
        @NotBlank
        String oldPass,
        @Password
        String newPass
        ) {

    public SettingDTO() {
        this(null, null);
    }
}
