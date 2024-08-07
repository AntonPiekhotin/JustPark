package com.parking.JustPark.repository;

import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    public boolean existsByParking(Parking parking);
    public List<ParkingLot> findAllByParking(Parking parking);
}
