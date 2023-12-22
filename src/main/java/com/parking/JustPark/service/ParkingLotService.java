package com.parking.JustPark.service;

import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.ParkingLot;
import com.parking.JustPark.repository.ParkingLotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public boolean createParkingLot(ParkingLot parkingLot, Parking parking) {
        parkingLot.setParking(parking);
        parkingLot.setIsEmpty(false);
        parkingLotRepository.save(parkingLot);
        log.info("Created parking lot {} in parking {}", parkingLot.getId(), parking.getId());
        return true;
    }

    public List<ParkingLot> listByParking(Parking parking) {
        if(parkingLotRepository.existsByParking(parking))
            return parkingLotRepository.findAllByParking(parking);
        log.info("No parking lots in this parking {}", parking.getId());
        return null;
    }
}
