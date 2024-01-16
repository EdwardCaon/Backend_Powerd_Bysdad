package Powered_by.springboot.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "fav_player")
public class FavPlayer {

    @EmbeddedId
    private FavPlayerId id;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @MapsId("idPlayer")
    @JoinColumn(name = "id_player")
    private Player player;
}
