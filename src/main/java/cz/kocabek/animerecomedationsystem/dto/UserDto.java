package cz.kocabek.animerecomedationsystem.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link cz.kocabek.animerecomedationsystem.entity.User}
 */
@Value
public class UserDto implements Serializable {
    Long id;
    @Size(max = 255)
    String username;
    String gender;
    Instant birthday;
    String location;
    String joined;
    Double daysWatched;
    Double meanScore;
    Long watching;
    Long completed;
    Long onHold;
    Long dropped;
    Long planToWatch;
    Long totalEntries;
    Long rewatched;
    Long episodesWatched;
}