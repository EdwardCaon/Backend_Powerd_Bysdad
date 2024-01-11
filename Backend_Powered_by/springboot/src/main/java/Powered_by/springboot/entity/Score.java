package Powered_by.springboot.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score {

    @EmbeddedId
    private ScoreId id;

    @ManyToOne
    @MapsId("idGame")
    @JoinColumn(name = "id_game")
    private Game game;

    @ManyToOne
    @MapsId("idTeam")
    @JoinColumn(name = "id_team")
    private Team team;

    @Column(name = "total_win")
    private int totalWin;

    @Column(name = "total_lose")
    private int totalLose;

    @Column(name = "series_win")
    private int seriesWin;

    @Column(name = "series_loss")
    private int seriesLoss;

    @Column(name = "p1")
    private int p1;

    @Column(name = "p2")
    private int p2;

    @Column(name = "p3")
    private int p3;

    @Column(name = "p4")
    private int p4;

    @Column(name = "p5")
    private int p5;

    @Column(name = "total_points")
    private int totalPoints;

    // Constructors, getters (if needed), no setters

    public ScoreId getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Team getTeam() {
        return team;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public int getTotalLose() {
        return totalLose;
    }

    public int getSeriesWin() {
        return seriesWin;
    }

    public int getSeriesLoss() {
        return seriesLoss;
    }

    public int getP1() {
        return p1;
    }

    public int getP2() {
        return p2;
    }

    public int getP3() {
        return p3;
    }

    public int getP4() {
        return p4;
    }

    public int getP5() {
        return p5;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
