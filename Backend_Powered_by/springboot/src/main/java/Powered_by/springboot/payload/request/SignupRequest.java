package Powered_by.springboot.payload.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    //richiesta per la registrazione
    @NotBlank @Size(min = 4,max = 20)
    private String firstname;

    @NotBlank @Size(min = 4,max = 20)
    private String lastname;

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String password;

    @JsonIgnore
    private int admin = 0;





}

