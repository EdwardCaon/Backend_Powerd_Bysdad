package Powered_by.springboot.entity;

import jakarta.persistence.Column;

import java.io.Serializable;

public class FavGameId  implements Serializable {

    @Column(name = "id_user")
    private int idUser;
    @Column(name = "id_game")
    private int idGame;

}
