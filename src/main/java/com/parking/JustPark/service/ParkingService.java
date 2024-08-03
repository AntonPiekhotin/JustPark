package com.parking.JustPark.service;

import com.parking.JustPark.model.dto.ParkingCreationDto;
import com.parking.JustPark.model.dto.ParkingResponseDto;
import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.ParkingRepository;
import com.parking.JustPark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final UserService userService;

    public User getAuthenticatedUser(String token) {
        return userService.getAuthenticatedUser(token);
    }

    public void createParking(ParkingCreationDto parkingDto, String token) {
        User currentUser = getAuthenticatedUser(token);
        Parking parking = Parking.builder()
                .title(parkingDto.getTitle())
                .address(parkingDto.getAddress())
                .city(parkingDto.getCity())
                .owner(currentUser)
                .build();
        parkingRepository.save(parking);
    }


    public Parking getParkingById(Long parkingId) {
        return parkingRepository.findById(parkingId).orElse(null);
    }

    public boolean deleteParking(Long parkingId) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking == null) {
            log.info("Parking with this id {} doesn`t exists", parkingId);
            return false;
        }
        parkingRepository.delete(parking);
        log.info("Deleted parking {}", parkingId);
        return false;
    }

    public boolean editParking(Parking parking) {
        if (parking != null) {
            parkingRepository.save(parking);
            log.info("Parking {} has been edited", parking.getId());
            return true;
        }
        log.info("Error occurred while editing parking {}, probably object is null", parking.getId());
        return false;
    }
}
