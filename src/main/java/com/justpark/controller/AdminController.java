package com.justpark.controller;

import com.justpark.model.dto.user.UserDto;
import com.justpark.model.entity.User;
import com.justpark.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .phoneNumber(user.getPhoneNumber())
                .registrationDate(user.getRegistrationDate())
                .country(user.getCountry())
                .roles(user.getRoles())
                .accountStatus(user.getAccountStatus())
                .build());
    }

    @GetMapping("/user/{id}/parkings")
    public ResponseEntity<?> getUserParkings(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserParkings(id));
    }

    @PutMapping("/user/ban/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        User user = adminService.banUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/unban/{id}")
    public ResponseEntity<?> unbanUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.unbanUser(id));
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

    @DeleteMapping("/parking/{id}")
    public ResponseEntity<?> deleteParking(@PathVariable Long id) {
        adminService.deleteParking(id);
        return ResponseEntity.ok("Parking " + id + " has been deleted");
    }

}
