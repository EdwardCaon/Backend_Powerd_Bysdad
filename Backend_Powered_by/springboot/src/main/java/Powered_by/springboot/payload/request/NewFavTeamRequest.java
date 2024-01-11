package Powered_by.springboot.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
@Getter
public class NewFavTeamRequest {

        //richiesta per il team nei preferiti
        @NotBlank
        private String token;

        @NotBlank
        private String nameTeam;



}
