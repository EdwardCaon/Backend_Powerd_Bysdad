package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TeamSeasonResponse {

    private int idPlayer;
    private String firsname;
    private String lastname;
    private String nameTeam;
    private String logo;
    private String country;
    private String eta;
    private Double height;
    private String pos;
    private Integer shirtNumber;
    private String img;
    private String logoCountry;

    public TeamSeasonResponse() {

    }
}
