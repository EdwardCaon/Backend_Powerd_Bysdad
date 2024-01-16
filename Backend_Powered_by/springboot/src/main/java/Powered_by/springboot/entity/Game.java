package Powered_by.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    private int idGame;

    @ManyToOne
    @JoinColumn(name = "home")
    private Team home;

    @ManyToOne
    @JoinColumn(name = "visitors")
    private Team visitors;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @Column(name = "duration")
    private java.sql.Time duration;

    @Column(name = "current_period")
    private Integer currentPeriod;

    @Column(name = "total_period")
    private Integer totalPeriod;

    @ManyToOne
    @JoinColumn(name = "status")
    private StatusGame status;

    @ManyToOne
    @JoinColumn(name = "arena")
    private Arena arena;

    @ManyToOne
    @JoinColumn(name = "season")
    private Season season;

    @Column(name = "official")
    private String official;

    // Constructors, getters, and setters (if needed)

    public int getIdGame() {
        return idGame;
    }

    public Team getHome() {
        return home;
    }

    public Team getVisitors() {
        return visitors;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public java.sql.Time getDuration() {
        return duration;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public Integer getTotalPeriod() {
        return totalPeriod;
    }

    public StatusGame getStatus() {
        return status;
    }

    public Arena getArena() {
        return arena;
    }

    public Season getSeason() {
        return season;
    }

    public String getOfficial() {
        return official;
    }
}
