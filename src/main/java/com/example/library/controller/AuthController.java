package com.example.library.controller;

import com.example.library.model.AppUser;
import com.example.library.model.Role;
import com.example.library.service.AppUserService;
import com.example.library.config.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AppUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AppUserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public AppUser register(@Valid @RequestBody RegisterRequest request) {
        Role role = request.role != null ? request.role : Role.USER;
        return userService.register(request.username, request.password, role);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username, request.password)
            );
            if (auth.isAuthenticated()) {
                return jwtUtil.generateToken(request.username);
            }
        } catch (AuthenticationException e) {
            // ignore
        }
        throw new RuntimeException("Invalid credentials");
    }

    public record RegisterRequest(@NotBlank String username, @NotBlank String password, Role role) {}
    public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
}
