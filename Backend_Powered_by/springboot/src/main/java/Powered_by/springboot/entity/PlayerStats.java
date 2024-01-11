package Powered_by.springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "player_stats")
public class PlayerStats {

    @EmbeddedId
    private PlayerStatsId id;

    @ManyToOne
    @MapsId("idPlayer")
    @JoinColumn(name = "id_player")
    private Player player;

    @ManyToOne
    @MapsId("idGame")
    @JoinColumn(name = "id_game")
    private Game game;

    @ManyToOne
    @MapsId("idTeam")
    @JoinColumn(name = "id_team")
    private Team team;

    private Integer points;

    @Column(length = 3)
    private String position;

    @Column(length = 255)
    private String minutes;

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

    private Integer assist;

    @Column(name = "personal_fouls")
    private Integer personalFouls;

    private Integer steals;

    private Integer turnovers;

    private Integer blocks;

    @Column(name = "plus_minus")
    private Integer plusMinus;

    // Constructors, getters (if needed), no setters

    public PlayerStatsId getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public Team getTeam() {
        return team;
    }

    public Integer getPoints() {
        return points;
    }

    public String getPosition() {
        return position;
    }

    public String getMinutes() {
        return minutes;
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

    // Altri metodi getter per le proprietà rimanenti
}

