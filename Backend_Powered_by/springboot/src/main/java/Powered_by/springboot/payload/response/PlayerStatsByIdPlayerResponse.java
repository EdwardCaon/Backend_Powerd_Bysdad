package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class PlayerStatsByIdPlayerResponse {
    private Long totalPoints;
    private Long totalRebounds;
    private Long totalAssists;
    private Long totalMinutes;
    private Long totalBlocks;
    private Long totalSteals;
    private Double freeThrowPercentage;
    private Double threePointPercentage;
    private Long fieldGoalsMade;

    // Costruttore, getter e setter...
    public PlayerStatsByIdPlayerResponse() {
    }
}

