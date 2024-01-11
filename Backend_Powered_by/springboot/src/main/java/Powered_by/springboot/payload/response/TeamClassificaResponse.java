package Powered_by.springboot.payload.response;

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
    private Long victories;
    private Double winPercentage;
    private Long lose;

    // Costruttore e metodi accessori possono essere generati da Lombok

}
