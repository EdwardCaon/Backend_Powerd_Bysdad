package Powered_by.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Embeddable
public class FavTeamId implements Serializable {


    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_team")
    private int idTeam;


}