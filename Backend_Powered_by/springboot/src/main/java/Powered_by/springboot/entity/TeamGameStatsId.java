package Powered_by.springboot.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class TeamGameStatsId implements Serializable {

    @Column(name = "id_game")
    private int idGame;

    @Column(name = "id_team")
    private int idTeam;

    // Constructors, getters (if needed), no setters

    public int getIdGame() {
        return idGame;
    }

    public int getIdTeam() {
        return idTeam;
    }
}
