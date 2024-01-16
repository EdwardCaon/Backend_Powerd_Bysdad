// PlayerStatsGameResponse.java
package Powered_by.springboot.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStatsGameResponse {
    private Integer idTeam;
    private Integer idPlayer;
    private Integer points;
    private Integer assist;
    private Integer totalRebounds;

    // No-argument constructor
    public PlayerStatsGameResponse() {
    }

    // Constructor with arguments
    public PlayerStatsGameResponse(Integer idTeam, Integer idPlayer, Integer points, Integer assist, Integer totalRebounds) {
        this.idTeam = idTeam;
        this.idPlayer = idPlayer;
        this.points = points;
        this.assist = assist;
        this.totalRebounds = totalRebounds;
    }
}
