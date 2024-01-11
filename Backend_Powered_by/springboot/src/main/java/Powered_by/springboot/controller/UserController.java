package Powered_by.springboot.controller;

import Powered_by.springboot.payload.request.SigninRequest;
import Powered_by.springboot.payload.request.SignupRequest;
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


}
