package Powered_by.springboot.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

        private int id;
        private String firstname;
        private String lastname;
        private String email;
        private String token;
        private int admin;
    }


