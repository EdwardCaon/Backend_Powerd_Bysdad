package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class FavPlayerResponse {

        private int idPlayer;
        private String firstname;
        private String lastname;
        private Double height;
        private Double weight;

    }


