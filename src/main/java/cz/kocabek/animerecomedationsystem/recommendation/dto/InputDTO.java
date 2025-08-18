package cz.kocabek.animerecomedationsystem.recommendation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class InputDTO {
    @NotEmpty(message = "enter at least one character")
    private String animeName;
    private int minRating;
    private int maxUsers;
    private boolean onlyInAnimeGenres;

    private InputDTO() {
    }
}
