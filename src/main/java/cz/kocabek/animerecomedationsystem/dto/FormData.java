package cz.kocabek.animerecomedationsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class FormData {

    @NotEmpty(message = "enter at least one character")
    private String animeName;
}
