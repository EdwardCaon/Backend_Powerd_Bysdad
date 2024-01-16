package Powered_by.springboot.payload.response;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeamGameStatsResponse {

    private final String nameTeam;
    private Integer gameId;
    private Integer freeThrowsMade;
    private Integer pointsFromTwo;
    private Integer pointsFromThree;
    private Integer points;
    private Integer totReb;
    private Integer offensiveRebounds;
    private Integer defensiveRebounds;
    private Integer assists;
    private Float blocks;
    private Float steals;
    private Float turnovers;
    private Integer personalFouls;
    private Integer biggestLead;
    private String minutes;  // Cambiato da Duration a String

    public TeamGameStatsResponse(
            Integer gameId, String nameTeam, Integer freeThrowsMade, Integer pointsFromTwo, Integer pointsFromThree,
            Integer points, Integer totReb, Integer offensiveRebounds, Integer defensiveRebounds,
            Integer assists, Float blocks, Float steals, Float turnovers, Integer personalFouls,
            Integer biggestLead, Object start, Object end
    ) {
        this.gameId = gameId;
        this.nameTeam = nameTeam;
        this.freeThrowsMade = freeThrowsMade;
        this.pointsFromTwo = pointsFromTwo;
        this.pointsFromThree = pointsFromThree;
        this.points = points;
        this.totReb = totReb;
        this.offensiveRebounds = offensiveRebounds;
        this.defensiveRebounds = defensiveRebounds;
        this.assists = assists;
        this.blocks = blocks;
        this.steals = steals;
        this.turnovers = turnovers;
        this.personalFouls = personalFouls;
        this.biggestLead = biggestLead;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Verifica se start e end sono non nulli prima di procedere con il parsing
        if (start != null && end != null) {
            LocalDateTime startTime = LocalDateTime.parse(start.toString(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(end.toString(), formatter);

            // Calcola la durata e formatta il risultato
            this.minutes = getFormattedDuration(calculateDuration(startTime, endTime));
        } else {
            // Assegna un valore di default nel caso in cui start o end siano nulli
            this.minutes = "N/D";
        }
    }

    // ... altri metodi ...
    private Duration calculateDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    private String getFormattedDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }



    public String getMinutes() {
        return minutes;
    }

    public Integer getGameId() {
        return gameId;
    }
    public String getNameTeam(){
        return  nameTeam;
    }
    public Integer getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public Integer getPointsFromTwo() {
        return pointsFromTwo;
    }

    public Integer getPointsFromThree() {
        return pointsFromThree;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getTotReb() {
        return totReb;
    }

    public Integer getOffensiveRebounds() {
        return offensiveRebounds;
    }

    public Integer getDefensiveRebounds() {
        return defensiveRebounds;
    }

    public Integer getAssists() {
        return assists;
    }

    public Float getBlocks() {
        return blocks;
    }

    public Float getSteals() {
        return steals;
    }

    public Float getTurnovers() {
        return turnovers;
    }

    public Integer getPersonalFouls() {
        return personalFouls;
    }

    public Integer getBiggestLead() {
        return biggestLead;
    }

}
