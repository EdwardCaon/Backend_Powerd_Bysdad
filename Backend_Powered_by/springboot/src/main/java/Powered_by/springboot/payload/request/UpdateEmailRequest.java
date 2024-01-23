package Powered_by.springboot.payload.request;

import lombok.Getter;

@Getter

public class UpdateEmailRequest {

    private String token;
    private String email;

}
