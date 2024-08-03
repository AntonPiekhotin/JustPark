package com.parking.JustPark.controller;

import com.parking.JustPark.model.dto.ParkingCreationDto;
import com.parking.JustPark.model.dto.ParkingResponseDto;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.ParkingRepository;
import com.parking.JustPark.service.ParkingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
