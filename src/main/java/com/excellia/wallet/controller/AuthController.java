package com.excellia.wallet.controller;

import com.excellia.wallet.dto.LoginRequest;
import com.excellia.wallet.dto.LoginResponse;
import com.excellia.wallet.dto.RegisterRequest;
import com.excellia.wallet.entity.User;
import com.excellia.wallet.security.JwtUtil;
import com.excellia.wallet.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(
                request.getPhoneNumber(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()
                )
        );
        User user = userService.findByPhoneNumber(request.getPhoneNumber());
        String token = jwtUtil.generateToken(user.getPhoneNumber(), user.getId());
        return new LoginResponse(token, user.getId(), user.getFirstName(), user.getLastName());
    }
}