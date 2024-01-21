package Powered_by.springboot.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "fav_game")
public class FavGame {

        @EmbeddedId
        private FavGameId id;

        @ManyToOne
        @MapsId("idUser")
        @JoinColumn(name = "id_user")
        private User user;

        @ManyToOne
        @MapsId("idGame")
        @JoinColumn(name = "id_game")
        private Game game;
    }




