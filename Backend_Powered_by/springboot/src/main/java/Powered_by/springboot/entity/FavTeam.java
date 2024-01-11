package Powered_by.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "fav_team")
public class FavTeam {

        @EmbeddedId
        private FavTeamId id;

        @ManyToOne
        @MapsId("idUser")
        @JoinColumn(name = "id_user")
        private User user;

        @ManyToOne
        @MapsId("idTeam")
        @JoinColumn(name = "id_team")
        private Team team;



}
