package Powered_by.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Embeddable
public class FavPlayerId implements Serializable {


    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_player")
    private int idPlayer;


}