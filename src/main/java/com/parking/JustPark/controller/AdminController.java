package com.parking.JustPark.controller;

import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.service.ParkingService;
import com.parking.JustPark.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ParkingService parkingService;


    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(Objects.requireNonNullElse(user, "User not found"));
    }

    @PutMapping("/user/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        User user = userService.banUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/unban/{id}")
    public ResponseEntity<?> unbanUser(@PathVariable Long id) {
        User user = userService.unbanUser(id);
        return ResponseEntity.ok(user);
    }

}
