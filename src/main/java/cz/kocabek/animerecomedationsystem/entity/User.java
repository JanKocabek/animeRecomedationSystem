package cz.kocabek.animerecomedationsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "mydatabase")
public class User {
    @Id
    @Column(name = "`Mal ID`", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Size(max = 255)
    @Column(name = "Username")
    private String username;

    @Lob
    @Column(name = "Gender")
    private String gender;

    @Column(name = "Birthday")
    private Instant birthday;

    @Lob
    @Column(name = "Location")
    private String location;

    @Lob
    @Column(name = "Joined")
    private String joined;

    @Column(name = "`Days Watched`")
    private Double daysWatched;

    @Column(name = "`Mean Score`")
    private Double meanScore;

    @Column(name = "Watching", columnDefinition = "int UNSIGNED")
    private Long watching;

    @Column(name = "Completed", columnDefinition = "int UNSIGNED")
    private Long completed;

    @Column(name = "`On Hold`", columnDefinition = "int UNSIGNED")
    private Long onHold;

    @Column(name = "Dropped", columnDefinition = "int UNSIGNED")
    private Long dropped;

    @Column(name = "`Plan to Watch`", columnDefinition = "int UNSIGNED")
    private Long planToWatch;

    @Column(name = "`Total Entries`", columnDefinition = "int UNSIGNED")
    private Long totalEntries;

    @Column(name = "Rewatched", columnDefinition = "int UNSIGNED")
    private Long rewatched;

    @Column(name = "`Episodes Watched`", columnDefinition = "int UNSIGNED")
    private Long episodesWatched;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private Set<UsersAnimeScore> usersAnimeScores = new LinkedHashSet<>();
}