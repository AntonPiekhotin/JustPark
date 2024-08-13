package com.justpark.controller;

import com.justpark.model.dto.user.UpdateUserDto;
import com.justpark.model.dto.user.UserDto;
import com.justpark.model.entity.User;
import com.justpark.service.UserService;
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
                .id(user.getId())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .phoneNumber(user.getPhoneNumber())
                .country(user.getCountry())
                .registrationDate(user.getRegistrationDate())
                .roles(user.getRoles())
                .accountStatus(user.getAccountStatus())
                .dateOfBirth(user.getDateOfBirth())
                .build();
        return ResponseEntity.ok(userToReturn);
    }

    @PutMapping("/my")
    public ResponseEntity<?> updateMyInfo(@Valid @RequestBody UpdateUserDto updateUserDto, @RequestHeader("Authorization") String token) {
        User user = userService.getAuthenticatedUser(token);
        User updatedUser = userService.updateUser(user, updateUserDto);
        UserDto userToReturn = UserDto.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .lastName(updatedUser.getLastName())
                .firstName(updatedUser.getFirstName())
                .phoneNumber(updatedUser.getPhoneNumber())
                .country(updatedUser.getCountry())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .registrationDate(updatedUser.getRegistrationDate())
                .roles(updatedUser.getRoles())
                .accountStatus(updatedUser.getAccountStatus())
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
