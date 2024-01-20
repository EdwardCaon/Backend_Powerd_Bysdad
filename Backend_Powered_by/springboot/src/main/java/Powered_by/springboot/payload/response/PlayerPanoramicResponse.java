package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerPanoramicResponse {
    private String logoTeam;
    private String nameTeam;
    private String eta;
    private String nameCountry;
    private String flagCountry;
    private Double height;
    private Double weight;
    private String pos;
    private Integer shirtNumber;

}
