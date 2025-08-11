package cz.kocabek.animerecomedationsystem.dto;

import cz.kocabek.animerecomedationsystem.entity.Anime;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link Anime}
 */

@Getter
@EqualsAndHashCode
@ToString
public final class AnimeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private final Long id;
    private final @NotNull String name;
    private final Double score;
    private final String imageURL;
    private final List<String> genres;

    /**
     *
     */
    public AnimeDto(Long id, @NotNull String name, Double score, String imageURL) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.imageURL = imageURL;
        this.genres = new ArrayList<>();
    }
}