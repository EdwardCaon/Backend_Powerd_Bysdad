package Powered_by.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TeamStatisticId implements Serializable {

    @Column(name = "id_team")
    private Integer idTeam;

    @Column(name = "id_season")
    private Integer idSeason;

    // equals and hashCode (generated or overridden as needed)
}
