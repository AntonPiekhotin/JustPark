package com.parking.JustPark.controller;

import com.parking.JustPark.model.dto.UpdateUserDto;
import com.parking.JustPark.model.dto.UserDto;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    private final UserService userService;

    @GetMapping("/my")
    public ResponseEntity<?> getMyInfo(@RequestHeader("Authorization") String token) {
        User user = userService.getAuthenticatedUser(token);
        UserDto userToReturn = UserDto.builder()
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .phoneNumber(user.getPhoneNumber())
                .country(user.getCountry())
                .registrationDate(user.getRegistrationDate())
                .build();
        return ResponseEntity.ok(userToReturn);
    }

    @PutMapping("/my")
    public ResponseEntity<?> updateMyInfo(@Valid @RequestBody UpdateUserDto updateUserDto, @RequestHeader("Authorization") String token) {
        User user = userService.getAuthenticatedUser(token);
        User updatedUser = userService.updateUser(user, updateUserDto);
        UserDto userToReturn = UserDto.builder()
                .email(updatedUser.getEmail())
                .lastName(updatedUser.getLastName())
                .firstName(updatedUser.getFirstName())
                .phoneNumber(updatedUser.getPhoneNumber())
                .country(updatedUser.getCountry())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .registrationDate(updatedUser.getRegistrationDate())
                .build();
        return ResponseEntity.ok(userToReturn);
    }

    @DeleteMapping("/my")
    public ResponseEntity<?> deleteMyAccount(@RequestHeader("Authorization") String token) {
        User user = userService.getAuthenticatedUser(token);
        userService.deleteUser(user);
        return ResponseEntity.status(200).body("User deleted successfully");
    }

}
