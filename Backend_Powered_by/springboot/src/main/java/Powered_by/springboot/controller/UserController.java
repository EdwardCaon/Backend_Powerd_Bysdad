package Powered_by.springboot.controller;

import Powered_by.springboot.payload.request.*;
import Powered_by.springboot.payload.response.UpdateFirstnameResponse;
import Powered_by.springboot.payload.response.UpdateLastnameResponse;
import Powered_by.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> save(@RequestBody @Valid SignupRequest request) {
        return userService.save(request);
    }


    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody @Valid SigninRequest request) {
        return userService.signin(request);
    }

    @PostMapping("update/firstname")
    public UpdateFirstnameResponse updateFirstname(@RequestBody @Valid UpdateFirstnameRequest request) {
        return userService.updateFirstname(request);
    }
    @PostMapping("update/lastname")
    public UpdateLastnameResponse updateLastname(@RequestBody @Valid UpdateLastnameRequest request) {
        return userService.updateLastname(request);
    }
    @PostMapping("update/email")
    public UpdateEmailResponse updateEmail(@RequestBody @Valid UpdateEmailRequest request) {
        return userService.updateEmail(request);
    }


}
