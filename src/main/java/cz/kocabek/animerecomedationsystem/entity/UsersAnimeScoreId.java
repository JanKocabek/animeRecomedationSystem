package cz.kocabek.animerecomedationsystem.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UsersAnimeScoreId implements Serializable {
    private static final long serialVersionUID = 3479452094872909133L;
}