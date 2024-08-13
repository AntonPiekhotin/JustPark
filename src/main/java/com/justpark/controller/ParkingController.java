package com.justpark.controller;

import com.justpark.exception.ResponseErrorDto;
import com.justpark.model.dto.parking.ParkingCreationDto;
import com.justpark.model.dto.parking.ParkingDto;
import com.justpark.model.dto.parking.ParkingCreationResponseDto;
import com.justpark.model.dto.parking.UpdateParkingDto;
import com.justpark.model.dto.parkingLot.ParkingLotResponseDto;
import com.justpark.model.entity.Parking;
import com.justpark.model.request.ParkingLotCreationRequest;
import com.justpark.service.ParkingLotService;
import com.justpark.service.ParkingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final ParkingLotService parkingLotService;

    @PostMapping("/create")
    public ResponseEntity<?> createParking(@Valid @RequestBody ParkingCreationDto parkingDto, @RequestHeader("Authorization") String token) {
        Parking parking = parkingService.createParking(parkingDto, token);
        ParkingCreationResponseDto parkingToReturn = ParkingCreationResponseDto.builder()
                .id(parking.getId())
                .title(parking.getTitle())
                .address(parking.getAddress())
                .city(parking.getCity())
                .build();
        return ResponseEntity.ok(parkingToReturn);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getMyParkings(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(parkingService.getMyParkings(token));
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
        return ResponseEntity.ok(parkingService.getParkingById(id, token));
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
        return ResponseEntity.ok("Parking deleted successfully");
    }

    @PostMapping("/{id}/lots")
    public ResponseEntity<?> createParkingLot(
            @PathVariable Long id,
            @RequestBody @Valid ParkingLotCreationRequest request,
            @RequestHeader("Authorization") String token) {
        List<ParkingLotResponseDto> parkingLotsResponse = parkingLotService.createParkingLot(id, request.getParkingLots(), token);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingLotsResponse);
    }

    @GetMapping("/{id}/lots")
    public ResponseEntity<?> getAllParkingLots(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        List<ParkingLotResponseDto> parkingLots = parkingLotService.listByParking(id, token);
        return ResponseEntity.ok(parkingLots);
    }

    @GetMapping("/{id}/lots/{lotId}")
    public ResponseEntity<?> getParkingLotById(
            @PathVariable Long id,
            @PathVariable Long lotId,
            @RequestHeader("Authorization") String token) {
        ParkingLotResponseDto parkingLot = parkingLotService.getParkingLotById(id, lotId, token);
        return ResponseEntity.ok(parkingLot);
    }

}
