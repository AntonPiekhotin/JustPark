package com.parking.JustPark.service;

import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.User;
import com.parking.JustPark.repository.ParkingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParkingService {
    private final ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public boolean createParking(Parking parking, User owner) {
        parking.setOwner(owner);
        parkingRepository.save(parking);
        log.info("Created parking {} by user {}", parking.getId(), owner.getId());
        return true;
    }

    public List<Parking> parkingList(User owner) {
        if(parkingRepository.existsByOwner(owner))
            return parkingRepository.findAllByOwner(owner);
        log.info("No parkings by this owner");
        return null;
    }
}
