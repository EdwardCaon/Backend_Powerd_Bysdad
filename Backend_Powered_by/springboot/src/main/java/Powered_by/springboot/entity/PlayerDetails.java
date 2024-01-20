package Powered_by.springboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_details")
public class PlayerDetails {

    @EmbeddedId
    private PlayerDetailsId id;

    @ManyToOne
    @MapsId("idPlayer")
    @JoinColumn(name = "id_player")
    private Player player;

    @ManyToOne
    @MapsId("idTeam")
    @JoinColumn(name = "id_team")
    private Team team;

    @ManyToOne
    @MapsId("idSeason")
    @JoinColumn(name = "id_season")
    private Season season;

    private String pos;

    private Boolean active;

    @Column(name = "shirt_number")
    private Integer shirtNumber;
    @Column(name = "img")
    private String img;

    // Constructors, getters (if needed), no setters for embedded ID

    public PlayerDetailsId getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public Season getSeason() {
        return season;
    }

    public String getPos() {
        return pos;
    }

    public Boolean getActive() {
        return active;
    }

    public Integer getShirtNumber() {
        return shirtNumber;
    }
    public String getImg(){return  img;}
}
