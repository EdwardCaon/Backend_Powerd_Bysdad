package Powered_by.springboot.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateFirstnameRequest {

    private String token;
    private String firstname;

}
