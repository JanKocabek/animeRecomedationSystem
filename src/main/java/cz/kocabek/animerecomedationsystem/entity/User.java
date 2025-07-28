package cz.kocabek.animerecomedationsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "`Mal ID`", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @OneToMany(mappedBy = "user")
    private Set<UsersAnimeScore> usersAnimeScores = new LinkedHashSet<>();

}