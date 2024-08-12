package com.parking.JustPark.service;

import com.parking.JustPark.exception.JustParkException;
import com.parking.JustPark.model.dto.ParkingLotCreationDto;
import com.parking.JustPark.model.dto.ParkingLotResponseDto;
import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.ParkingLot;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.ParkingLotRepository;
import com.parking.JustPark.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;
    private final ParkingRepository parkingRepository;
    private final UserService userService;

    private final String NO_ACCESS_MESSAGE = "You have no access to this resource";

    private User getAuthenticatedUser(String token) {
        return userService.getAuthenticatedUser(token);
    }

    public List<ParkingLotResponseDto> createParkingLot(Long parkingId, List<ParkingLotCreationDto> parkingLots, String token) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking == null) {
            throw new JustParkException("Parking with given id not found", HttpStatus.BAD_REQUEST);
        }
        checkOwner(parking, token);
        List<ParkingLot> newParkingLots = new LinkedList<>();

        parkingLots.forEach(parkingLot -> {
            ParkingLot newParkingLot = ParkingLot.builder()
                    .title(parkingLot.getTitle())
                    .isEmpty(true)
                    .layer(parkingLot.getLayer())
                    .parking(parking)
                    .build();
            newParkingLots.add(newParkingLot);
            parkingLotRepository.save(newParkingLot);
        });

        return newParkingLots.stream()
                .map(parkingLot ->
                        ParkingLotResponseDto.builder()
                                .id(parkingLot.getId())
                                .parkingId(parkingLot.getParking().getId())
                                .title(parkingLot.getTitle())
                                .layer(parkingLot.getLayer())
                                .isEmpty(false)
                                .build())
                .toList();
    }

    private void checkOwner(Parking parking, String token) {
        User currentUser = getAuthenticatedUser(token);
        if (parking == null) {
            throw new JustParkException("Parking with given id not found", HttpStatus.BAD_REQUEST);
        }
        if (!parking.getOwner().getId().equals(currentUser.getId())) {
            throw new JustParkException(NO_ACCESS_MESSAGE, HttpStatus.FORBIDDEN);
        }
    }

    public List<ParkingLotResponseDto> listByParking(Long parkingId, String token) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking == null) {
            throw new JustParkException("Parking with given id not found", HttpStatus.BAD_REQUEST);
        }
        checkOwner(parking, token);
        List<ParkingLot> parkingLots = parkingLotRepository.findAllByParking(parking);
        if (parkingLots.isEmpty())
            return null;

        List<ParkingLotResponseDto> parkingLotsResponse = new ArrayList<>();
        parkingLots.forEach(parkingLot -> {
            parkingLotsResponse.add(ParkingLotResponseDto.builder()
                    .id(parkingLot.getId())
                    .parkingId(parkingLot.getParking().getId())
                    .title(parkingLot.getTitle())
                    .layer(parkingLot.getLayer())
                    .isEmpty(parkingLot.getIsEmpty())
                    .build()
            );
        });
        return parkingLotsResponse;
    }

    public ParkingLotResponseDto getParkingLotById(Long parkingId, Long parkingLotId, String token) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking == null) {
            throw new JustParkException("Parking with given id not found", HttpStatus.BAD_REQUEST);
        }
        checkOwner(parking, token);
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
        if (parkingLot == null) {
            throw new JustParkException("Parking lot with given id not found", HttpStatus.BAD_REQUEST);
        }
        return ParkingLotResponseDto.builder()
                .id(parkingLot.getId())
                .parkingId(parkingLot.getParking().getId())
                .title(parkingLot.getTitle())
                .layer(parkingLot.getLayer())
                .isEmpty(parkingLot.getIsEmpty())
                .build();
    }


}
