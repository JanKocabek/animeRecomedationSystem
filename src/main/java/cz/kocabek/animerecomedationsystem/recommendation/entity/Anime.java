package cz.kocabek.animerecomedationsystem.recommendation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "anime")
public class Anime {
    @Id
    @Column(name = "anime_id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @NotNull
    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "`English name`")
    private String englishName;

    @Column(name = "Score")
    private Double score;

    @Lob
    @Column(name = "Type")
    private String type;

    @Column(name = "Episodes")
    private Long episodes;

    @Lob
    @Column(name = "Status")
    private String status;

    @Lob
    @Column(name = "Source")
    private String source;

    @Lob
    @Column(name = "Rating")
    private String rating;

    @Column(name = "Rank")
    private Integer rank;

    @Column(name = "Popularity")
    private Integer popularity;

    @Lob
    @Column(name = "`Image URL`")
    private String imageURL;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new LinkedHashSet<>();

}