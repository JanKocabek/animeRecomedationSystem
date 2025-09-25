package cz.kocabek.animerecomedationsystem.security.dto;

import cz.kocabek.animerecomedationsystem.security.validation.Password;

public record SettingDTO(
        String oldPass,
        @Password
        String newPass
        ) {

    public SettingDTO() {
        this(null, null);
    }
}
