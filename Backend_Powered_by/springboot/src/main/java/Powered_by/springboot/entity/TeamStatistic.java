package Powered_by.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team_statistic")
public class TeamStatistic {

    @EmbeddedId
    private TeamStatisticId id;

    @ManyToOne
    @MapsId("idTeam")
    @JoinColumn(name = "id_team")
    private Team team;

    @ManyToOne
    @MapsId("idSeason")
    @JoinColumn(name = "id_season")
    private Season season;

    @Column(name = "games_played")
    private Integer gamesPlayed;

    @Column(name = "fast_break_points")
    private Integer fastBreakPoints;

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

    @Column(name = "field_goals_made")
    private Integer fieldGoalsMade;

    @Column(name = "field_goals_attempted")
    private Integer fieldGoalsAttempted;

    @Column(name = "field_goal_percentage")
    private Double fieldGoalPercentage;

    @Column(name = "free_throws_made")
    private Integer freeThrowsMade;

    @Column(name = "free_throws_attempted")
    private Integer freeThrowsAttempted;

    @Column(name = "free_throw_percentage")
    private Double freeThrowPercentage;

    @Column(name = "three_pointers_made")
    private Integer threePointersMade;

    @Column(name = "three_pointers_attempted")
    private Integer threePointersAttempted;

    @Column(name = "three_point_percentage")
    private Double threePointPercentage;

    @Column(name = "offensive_rebounds")
    private Integer offensiveRebounds;

    @Column(name = "defensive_rebounds")
    private Integer defensiveRebounds;

    @Column(name = "total_rebounds")
    private Integer totalRebounds;

    @Column(name = "assist")
    private Integer assist;

    @Column(name = "personal_fouls")
    private Integer personalFouls;

    @Column(name = "steals")
    private Integer steals;

    @Column(name = "turnovers")
    private Integer turnovers;

    @Column(name = "blocks")
    private Integer blocks;

    @Column(name = "plus_minus")
    private Integer plusMinus;

    // Constructors and getters (if needed), no setters for embedded ID

    public TeamStatisticId getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public Season getSeason() {
        return season;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public Integer getFastBreakPoints() {
        return fastBreakPoints;
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

    public Integer getFieldGoalsMade() {
        return fieldGoalsMade;
    }

    public Integer getFieldGoalsAttempted() {
        return fieldGoalsAttempted;
    }

    public Double getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }

    public Integer getFreeThrowsMade() {
        return freeThrowsMade;
    }

    public Integer getFreeThrowsAttempted() {
        return freeThrowsAttempted;
    }

    public Double getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    public Integer getThreePointersMade() {
        return threePointersMade;
    }

    public Integer getThreePointersAttempted() {
        return threePointersAttempted;
    }

    public Double getThreePointPercentage() {
        return threePointPercentage;
    }

    public Integer getOffensiveRebounds() {
        return offensiveRebounds;
    }

    public Integer getDefensiveRebounds() {
        return defensiveRebounds;
    }

    public Integer getTotalRebounds() {
        return totalRebounds;
    }

    public Integer getAssist() {
        return assist;
    }

    public Integer getPersonalFouls() {
        return personalFouls;
    }

    public Integer getSteals() {
        return steals;
    }

    public Integer getTurnovers() {
        return turnovers;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public Integer getPlusMinus() {
        return plusMinus;
    }

}
