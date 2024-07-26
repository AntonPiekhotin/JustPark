package com.parking.JustPark.controller;

import com.parking.JustPark.model.dto.LoginResponseDto;
import com.parking.JustPark.model.dto.LoginUserDto;
import com.parking.JustPark.model.dto.RegisterResponseDto;
import com.parking.JustPark.model.dto.RegisterUserDto;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.service.security.jwt.AuthenticationService;
import com.parking.JustPark.service.security.jwt.JwtService;
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

//    @GetMapping("/export-users")
//    public ResponseEntity<?> exportUsers() {
//        return ResponseEntity.ok().body(userService.exportUsers());
//    }
//
//    @PostMapping("/edit-user/{id}")
//    public ResponseEntity<?> editUser(@RequestBody GuestInfoDto guestInfoDto, @PathVariable Long id) {
//        if (guestInfoDto == null) {
//            return ResponseEntity.badRequest().body("Invalid data");
//        }
//        if (userService.editUser(id, guestInfoDto)) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().body("Error while editing user");
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseEntity<?> getUserById(@PathVariable Long id) {
//        if (id == null || id <= 0) {
//            return ResponseEntity.badRequest().body("Invalid id");
//        }
//        if (!userService.existsById(id)) {
//            return ResponseEntity.badRequest().body("User with this id not found");
//        }
//        return ResponseEntity.ok().body(userService.getUserInfoById(id));
//    }
//
//    @GetMapping("/users")
//    public ResponseEntity<?> getAllUsers() {
//        return ResponseEntity.ok().body(userService.getAllUsers());
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        if (id == null || id <= 0) {
//            return ResponseEntity.badRequest().body("Invalid id");
//        }
//        return ResponseEntity.ok().body(userService.deleteUserById(id));
//    }
//
//    @GetMapping("/backup")
//    public ResponseEntity<?> backup() {
//        return ResponseEntity.ok().body(userService.backupDatabase());
//    }

}

