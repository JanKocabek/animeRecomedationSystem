package cz.kocabek.animerecomedationsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public final class ConfigForm {
    @NotEmpty(message = "enter at least one character")
    private String animeName;
    private int minRating = 7;
    private int maxUsers = 750;
}
