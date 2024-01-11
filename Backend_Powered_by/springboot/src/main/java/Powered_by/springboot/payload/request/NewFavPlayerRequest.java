package Powered_by.springboot.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NewFavPlayerRequest {

    //richiesta per il team nei preferiti
    @NotBlank
    private String token;

    @NotNull
    private Integer idPlayer;


}
