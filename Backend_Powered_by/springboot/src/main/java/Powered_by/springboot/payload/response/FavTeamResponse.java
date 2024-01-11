package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FavTeamResponse {
    private int idTeam;
    private String nameTeam;
    private String nickname;
    private String colour;
    private String logo;
    private String league;

}
