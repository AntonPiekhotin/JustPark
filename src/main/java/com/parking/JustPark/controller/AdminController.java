package com.parking.JustPark.controller;

import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.service.AdminService;
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

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = adminService.getUserById(id);
        return ResponseEntity.ok(Objects.requireNonNullElse(user, "User not found"));
    }

    @PutMapping("/user/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        User user = adminService.banUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/unban/{id}")
    public ResponseEntity<?> unbanUser(@PathVariable Long id) {
        User user = adminService.unbanUser(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User " + id + " has been deleted");
    }

    @GetMapping("/parkings")
    public ResponseEntity<?> getParkings() {
        return ResponseEntity.ok(adminService.getAllParkings());
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<?> getParkingById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getParkingById(id));
    }

}
