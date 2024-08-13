package com.justpark.controller;

import com.justpark.model.dto.user.LoginResponseDto;
import com.justpark.model.dto.user.LoginUserDto;
import com.justpark.model.dto.user.RegisterResponseDto;
import com.justpark.model.dto.user.RegisterUserDto;
import com.justpark.model.entity.User;
import com.justpark.service.security.jwt.AuthenticationService;
import com.justpark.service.security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        RegisterResponseDto registerResponse = RegisterResponseDto.builder()
                .email(registeredUser.getEmail())
                .roles(registeredUser.getRoles())
                .firstName(registeredUser.getFirstName())
                .lastName(registeredUser.getLastName())
                .phoneNumber(registeredUser.getPhoneNumber())
                .country(registeredUser.getCountry())
                .registrationDate(registeredUser.getRegistrationDate())
                .accountStatus(registeredUser.getAccountStatus())
                .build();

        return ResponseEntity.status(201).body(registerResponse);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .token(jwtToken)
                .expiresIn(jwtService.extractExpiration(jwtToken))
                .build();

        return ResponseEntity.ok(loginResponse);
    }

}

