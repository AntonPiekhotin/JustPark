package com.parking.JustPark.service;

import com.parking.JustPark.exception.NoAccessException;
import com.parking.JustPark.model.dto.ParkingCreationDto;
import com.parking.JustPark.model.dto.ParkingDto;
import com.parking.JustPark.model.dto.ParkingResponseDto;
import com.parking.JustPark.model.dto.UpdateParkingDto;
import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.ParkingRepository;
import com.parking.JustPark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Parking> getMyParkings(String token) {
        User currentUser = getAuthenticatedUser(token);
        List<Parking> parkings = parkingRepository.findAllByOwner(currentUser);
        parkings.forEach(parking -> parking.setOwner(null));
        return parkings;
    }


    public ParkingDto getParkingById(Long parkingId, String token) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking == null) {
            return null;
        }
        User currentUser = getAuthenticatedUser(token);
        if(!parking.getOwner().getId().equals(currentUser.getId())) {
            throw new NoAccessException();
        }
        return ParkingDto.builder()
                .id(parking.getId())
                .title(parking.getTitle())
                .address(parking.getAddress())
                .city(parking.getCity())
                .parkingLots(parking.getParkingLots())
                .ratings(parking.getRatings())
                .pricePerHour(parking.getPricePerHour())
                .build();
    }

    public ParkingDto updateParking(Long id, UpdateParkingDto updateParkingDto, String token) {
        User currentUser = getAuthenticatedUser(token);
        Parking parking = parkingRepository.findById(id).orElse(null);
        if (parking == null) {
            return null;
        }
        if(!parking.getOwner().getId().equals(currentUser.getId())) {
            throw new NoAccessException();
        }
        parking.setTitle(updateParkingDto.getTitle());
        parking.setAddress(updateParkingDto.getAddress());
        parking.setCity(updateParkingDto.getCity());
        parkingRepository.save(parking);
        return ParkingDto.builder()
                .id(parking.getId())
                .title(parking.getTitle())
                .address(parking.getAddress())
                .city(parking.getCity())
                .parkingLots(parking.getParkingLots())
                .ratings(parking.getRatings())
                .pricePerHour(parking.getPricePerHour())
                .build();
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
}
