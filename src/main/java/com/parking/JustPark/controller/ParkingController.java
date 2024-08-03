package com.parking.JustPark.controller;

import com.parking.JustPark.exception.ResponseErrorDto;
import com.parking.JustPark.model.dto.ParkingCreationDto;
import com.parking.JustPark.model.dto.ParkingDto;
import com.parking.JustPark.model.dto.ParkingResponseDto;
import com.parking.JustPark.model.dto.UpdateParkingDto;
import com.parking.JustPark.service.ParkingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user/my/parking")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class ParkingController {

    private final ParkingService parkingService;

    @PostMapping("/create")
    public ResponseEntity<?> createParking(@Valid @RequestBody ParkingCreationDto parkingDto, @RequestHeader("Authorization") String token) {
        parkingService.createParking(parkingDto, token);
        ParkingResponseDto parkingToReturn = ParkingResponseDto.builder()
                .title(parkingDto.getTitle())
                .address(parkingDto.getAddress())
                .city(parkingDto.getCity())
                .build();
        return ResponseEntity.ok(parkingToReturn);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getMyParkings(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(parkingService.getMyParkings(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        ParkingDto parking = parkingService.getParkingById(id, token);
        if (parking == null) {
            return ResponseEntity.badRequest().body(ResponseErrorDto.builder()
                    .time(LocalDateTime.now())
                    .statusCode("400")
                    .errorMessage(List.of("Parking with id not found"))
                    .build());
        }
        return ResponseEntity.ok().body(parkingService.getParkingById(id, token));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParking(
            @PathVariable Long id,
            @Valid @RequestBody UpdateParkingDto parkingDto,
            @RequestHeader("Authorization") String token) {
        ParkingDto parking = parkingService.updateParking(id, parkingDto, token);
        if (parking == null) {
            return ResponseEntity.badRequest().body(ResponseErrorDto.builder()
                    .time(LocalDateTime.now())
                    .statusCode("400")
                    .errorMessage(List.of("Parking with this id not found"))
                    .build());
        }
        return ResponseEntity.ok(parking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParking(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!parkingService.deleteParking(id, token)) {
            return ResponseEntity.badRequest().body(ResponseErrorDto.builder()
                    .time(LocalDateTime.now())
                    .statusCode("400")
                    .errorMessage(List.of("Parking with id not found"))
                    .build());
        }
        return ResponseEntity.ok().body("Parking deleted successfully");
    }
}
