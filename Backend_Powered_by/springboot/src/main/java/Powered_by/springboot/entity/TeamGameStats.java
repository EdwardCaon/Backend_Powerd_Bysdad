package Powered_by.springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "team_game_stats")
public class TeamGameStats {

    @EmbeddedId
    private TeamGameStatsId id;

    @ManyToOne
    @MapsId("idGame")
    @JoinColumn(name = "id_game")
    private Game game;

    @ManyToOne
    @MapsId("idTeam")
    @JoinColumn(name = "id_team")
    private Team team;

    @Column(name = "points_in_paint")
    private Integer pointsInPaint;

    @Column(name = "biggest_lead")
    private Integer biggestLead;

    @Column(name = "second_chance_points")
    private Integer secondChancePoints;

    @Column(name = "points_off_turnovers")
    private Integer pointsOffTurnovers;

    @Column(name = "longest_run")
    private Integer longestRun;

    @Column(name = "points")
    private Integer points;

    @Column(name = "fgm")
    private Integer fgm;

    @Column(name = "fga")
    private Integer fga;

    @Column(name = "fgp")
    private String fgp;

    @Column(name = "ftm")
    private Integer ftm;

    @Column(name = "fta")
    private Integer fta;

    @Column(name = "ftp")
    private String ftp;

    @Column(name = "tpm")
    private Integer tpm;

    @Column(name = "tpa")
    private Integer tpa;

    @Column(name = "tpp")
    private String tpp;

    @Column(name = "off_reb")
    private Integer offReb;

    @Column(name = "tot_reb")
    private Integer totReb;

    @Column(name = "assists")
    private Integer assists;

    @Column(name = "pFouls")
    private Integer pFouls;

    @Column(name = "steals")
    private Float steals;

    @Column(name = "turnovers")
    private Float turnovers;

    @Column(name = "blocks")
    private Float blocks;

    @Column(name = "plus_minus")
    private String plusMinus;

    @Column(name = "min")
    private String min;

    // Constructors, getters (if needed), no setters

    public TeamGameStatsId getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Team getTeam() {
        return team;
    }

    public Integer getPointsInPaint() {
        return pointsInPaint;
    }

    public Integer getBiggestLead() {
        return biggestLead;
    }

    public Integer getSecondChancePoints() {
        return secondChancePoints;
    }

    public Integer getPointsOffTurnovers() {
        return pointsOffTurnovers;
    }

    public Integer getLongestRun() {
        return longestRun;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getFgm() {
        return fgm;
    }

    public Integer getFga() {
        return fga;
    }

    public String getFgp() {
        return fgp;
    }

    public Integer getFtm() {
        return ftm;
    }

    public Integer getFta() {
        return fta;
    }

    public String getFtp() {
        return ftp;
    }

    public Integer getTpm() {
        return tpm;
    }

    public Integer getTpa() {
        return tpa;
    }

    public String getTpp() {
        return tpp;
    }

    public Integer getOffReb() {
        return offReb;
    }

    public Integer getTotReb() {
        return totReb;
    }

    public Integer getAssists() {
        return assists;
    }

    public Integer getpFouls() {
        return pFouls;
    }

    public Float getSteals() {
        return steals;
    }

    public Float getTurnovers() {
        return turnovers;
    }

    public Float getBlocks() {
        return blocks;
    }

    public String getPlusMinus() {
        return plusMinus;
    }

    public String getMin() {
        return min;
    }

    // Altri metodi getter per le proprietà rimanenti
}
