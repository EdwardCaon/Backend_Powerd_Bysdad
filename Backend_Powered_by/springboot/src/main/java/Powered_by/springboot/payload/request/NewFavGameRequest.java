package Powered_by.springboot.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewFavGameRequest {

    private String token;
    private Integer idGame;
}
