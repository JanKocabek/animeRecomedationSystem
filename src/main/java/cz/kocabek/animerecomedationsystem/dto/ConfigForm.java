package cz.kocabek.animerecomedationsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ConfigForm {
    @NotEmpty(message = "enter at least one character")
    private String animeName;
    private int minRating;
    private int maxUsers;
    private boolean onlyInAnimeGenres;

    private ConfigForm() {
    }
}
