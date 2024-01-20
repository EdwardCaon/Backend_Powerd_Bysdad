package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerSearchBarResponse {

    private int idPlayer;
    private String firstname;
    private String lastname;
    private String img;
}
