package com.parking.JustPark.service;

import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.User;
import com.parking.JustPark.repository.ParkingRepository;
import com.parking.JustPark.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository, UserRepository userRepository) {
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
    }

    public boolean createParking(Parking parking, Long ownerId) {
        User owner = userRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            log.info("User with this id {} doesn`t exists", ownerId);
            return false;
        }
        parking.setOwner(owner);
        parkingRepository.save(parking);
        log.info("Created parking {} by user {}", parking.getId(), owner.getId());
        return true;
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
