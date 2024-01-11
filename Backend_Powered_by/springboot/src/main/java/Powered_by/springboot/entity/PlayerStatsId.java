package Powered_by.springboot.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class PlayerStatsId implements Serializable {

    @Column(name = "id_player")
    private Integer idPlayer;

    @Column(name = "id_team")
    private Integer idTeam;

    @Column(name = "id_game")
    private Integer idGame;

    // Constructors, equals, and hashCode methods

    // Altri metodi se necessario
}
