package cz.kocabek.animerecomedationsystem.security.dto;

import cz.kocabek.animerecomedationsystem.security.validation.Password;

public record DeletingCheckDTO(
        @Password
        String password
        ) {

    public DeletingCheckDTO() {
        this(null);
    }
}
