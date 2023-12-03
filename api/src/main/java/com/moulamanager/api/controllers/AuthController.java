package com.moulamanager.api.controllers;

import com.moulamanager.api.dto.user.request.LoginRequestDTO;
import com.moulamanager.api.dto.user.request.CreateUserRequestDTO;
import com.moulamanager.api.dto.user.result.LoginResultDTO;
import com.moulamanager.api.dto.user.result.UserResultDTO;
import com.moulamanager.api.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResultDTO> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        LoginResultDTO loginResult = userService.authenticateUser(loginRequest);
        return ResponseEntity.ok(loginResult);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResultDTO> registerUser(@RequestBody CreateUserRequestDTO signUpRequest) {
        UserResultDTO userResult = userService.createUser(signUpRequest);
        return ResponseEntity.ok(userResult);
    }
}