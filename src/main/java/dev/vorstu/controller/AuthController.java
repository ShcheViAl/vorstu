package dev.vorstu.controller;

import dev.vorstu.dto.auth.AuthResponse;
import dev.vorstu.dto.auth.LoginRequest;
import dev.vorstu.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
