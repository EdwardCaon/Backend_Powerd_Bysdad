package Powered_by.springboot.payload.response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamClassificaResponse {
    private int teamId;
    private String teamLogo;
    private String teamName;
    private String colour;
    private Integer victories;
    private Double winPercentage;
    private Integer lose;
    private Integer gamePlayed;
    private Integer diffPoints;

    // Costruttore e metodi accessori possono essere generati da Lombok

}
