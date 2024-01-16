package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
//panoramica giocatore
public class FavPlayerResponse {

        private int idPlayer;
        private String firstname;
        private String lastname;
        private Double height;
        private Double weight;
        //ruolo principale
        //numero maglia
        //nazionalita
        //eta
    }


