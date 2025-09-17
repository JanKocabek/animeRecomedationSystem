package cz.kocabek.animerecomedationsystem.recommendation.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "Type")
    private String type;

    @Column(name = "Episodes")
    private Integer episodes;

    @Column(name = "Status")
    private String status;

    @Column(name = "Source")
    private String source;

    @Column(name = "Rating")
    private String rating;

    @Column(name = "Rank")
    private Integer rank;

    @Column(name = "Popularity")
    private Integer popularity;

    @Column(name = "`Image URL`")
    private String imageURL;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new LinkedHashSet<>();

}
