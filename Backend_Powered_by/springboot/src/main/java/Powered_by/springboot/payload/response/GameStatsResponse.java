package Powered_by.springboot.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class GameStatsResponse {

    private int gameId;
    private String arenaName;
    private String city;
    private String homeTeamName;
    private String homeTeamLogo;
    private String visitorTeamName;
    private String visitorTeamLogo;
    private Integer pointsInPaint;
    private Integer biggestLead;
    private Integer secondChancePoints;
    private Integer pointsOffTurnovers;
    private Integer longestRun;
    private Integer points;
    private Integer fgm;
    private Integer fga;
    private String fgp;
    private Integer ftm;
    private Integer fta;
    private String ftp;
    private Integer tpm;
    private Integer tpa;
    private String tpp;
    private Integer offReb;
    private Integer totReb;
    private Integer assists;
    private Integer pFouls;
    private Integer steals;
    private Float turnovers;
    private Float blocks;
    private String plusMinus;
    private String min;

    private Integer visitorPointsInPaint;
    private Integer visitorBiggestLead;
    private Integer visitorSecondChancePoints;
    private Integer visitorPointsOffTurnovers;
    private Integer visitorLongestRun;
    private Integer visitorPoints;
    private Integer visitorFgm;
    private Integer visitorFga;
    private String visitorFgp;
    private Integer visitorFtm;
    private Integer visitorFta;
    private String visitorFtp;
    private Integer visitorTpm;
    private Integer visitorTpa;
    private String visitorTpp;
    private Integer visitorOffReb;
    private Integer visitorTotReb;
    private Integer visitorAssists;
    private Integer visitorPFouls;
    private Float visitorSteals;
    private Float visitorTurnovers;
    private Float visitorBlocks;
    private String visitorPlusMinus;
    private String visitorMin;


    private String minutes;  // Cambiato da Duration a String
    private Integer p1Home;
    private Integer p2Home;
    private Integer p3Home;
    private Integer p4Home;
    private Integer p5Home;
    private Integer p1Visitors;
    private Integer p2Visitors;
    private Integer p3Visitors;
    private Integer p4Visitors;
    private Integer p5Visitors;







    public GameStatsResponse(
            Integer gameId, String arenaName, String city,
            String homeTeamName, String homeTeamLogo,
            String visitorTeamName, String visitorTeamLogo,
            Integer pointsInPaint, Integer biggestLead,
            Integer secondChancePoints, Integer pointsOffTurnovers,
            Integer longestRun, Integer points,
            Integer fgm, Integer fga, String fgp,
            Integer ftm, Integer fta, String ftp,
            Integer tpm, Integer tpa, String tpp,
            Integer offReb, Integer totReb, Integer assists,
            Integer pFouls, Integer steals, Float turnovers,
            Float blocks, String plusMinus, String min,
            Integer visitorPointsInPaint, Integer visitorBiggestLead,
            Integer visitorSecondChancePoints,
            Integer visitorPointsOffTurnovers, Integer visitorLongestRun,
            Integer visitorPoints,
            Integer visitorFgm, Integer visitorFga,
            String visitorFgp, Integer visitorFtm,
            Integer visitorFta, String visitorFtp,
            Integer visitorTpm, Integer visitorTpa,
            String visitorTpp, Integer visitorOffReb,
            Integer visitorTotReb, Integer visitorAssists,
            Integer visitorPFouls, Float visitorSteals,
            Float visitorTurnovers, Float visitorBlocks,
            String visitorPlusMinus, String visitorMin,
            String minutes,
            Integer p1Home,
            Integer p2Home ,
            Integer p3Home,
            Integer p4Home,
            Integer p5Home,
            Integer p1Visitors,
            Integer p2Visitors,
            Integer p3Visitors,
            Integer p4Visitors,
            Integer p5Visitors) {

        this.gameId = gameId;
        this.arenaName = arenaName;
        this.city = city;
        this.homeTeamName = homeTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.visitorTeamName = visitorTeamName;
        this.visitorTeamLogo = visitorTeamLogo;

        this.pointsInPaint = pointsInPaint;
        this.biggestLead = biggestLead;
        this.secondChancePoints = secondChancePoints;
        this.pointsOffTurnovers = pointsOffTurnovers;
        this.longestRun = longestRun;
        this.points = points;
        this.fgm = fgm;
        this.fga = fga;
        this.fgp = fgp;
        this.ftm = ftm;
        this.fta = fta;
        this.ftp = ftp;
        this.tpm = tpm;
        this.tpa = tpa;
        this.tpp = tpp;
        this.offReb = offReb;
        this.totReb = totReb;
        this.assists = assists;
        this.pFouls = pFouls;
        this.steals = steals;
        this.turnovers = turnovers;
        this.blocks = blocks;
        this.plusMinus = plusMinus;
        this.min = min;

        this.visitorPointsInPaint = visitorPointsInPaint;
        this.visitorBiggestLead = visitorBiggestLead;
        this.visitorSecondChancePoints = visitorSecondChancePoints;
        this.visitorPointsOffTurnovers = visitorPointsOffTurnovers;
        this.visitorLongestRun = visitorLongestRun;
        this.visitorPoints = visitorPoints;
        this.visitorFgm = visitorFgm;
        this.visitorFga = visitorFga;
        this.visitorFgp = visitorFgp;
        this.visitorFtm = visitorFtm;
        this.visitorFta = visitorFta;
        this.visitorFtp = visitorFtp;
        this.visitorTpm = visitorTpm;
        this.visitorTpa = visitorTpa;
        this.visitorTpp = visitorTpp;
        this.visitorOffReb = visitorOffReb;
        this.visitorTotReb = visitorTotReb;
        this.visitorAssists = visitorAssists;
        this.visitorPFouls = visitorPFouls;
        this.visitorSteals = visitorSteals;
        this.visitorTurnovers = visitorTurnovers;
        this.visitorBlocks = visitorBlocks;
        this.visitorPlusMinus = visitorPlusMinus;
        this.visitorMin = visitorMin;
        this.minutes = minutes;

        this.p1Home = p1Home;
        this.p2Home = p2Home;
        this.p3Home = p3Home;
        this.p4Home = p4Home;
        this.p5Home = p5Home;
        this.p1Visitors = p1Visitors;
        this.p2Visitors = p2Visitors;
        this.p3Visitors = p3Visitors;
        this.p4Visitors = p4Visitors;
        this.p5Visitors = p5Visitors;


    }

}

