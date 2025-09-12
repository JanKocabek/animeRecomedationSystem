package cz.kocabek.animerecomedationsystem.user.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AccWatchlistId implements Serializable {
    private static final long serialVersionUID = 3953028786396392471L;
    @NotNull
    @Column(name = "acc_id", nullable = false)
    private Integer accId;

    @NotNull
    @Column(name = "anime_id", nullable = false)
    private Long animeId;
}