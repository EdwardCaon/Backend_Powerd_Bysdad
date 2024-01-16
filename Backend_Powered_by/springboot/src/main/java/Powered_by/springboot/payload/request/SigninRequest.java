package Powered_by.springboot.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class SigninRequest {

    //richiesta per il login
    @NotBlank @Size(min = 4,max = 50)
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

}
