package com.justpark.repository;

import com.justpark.model.entity.Parking;
import com.justpark.model.entity.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    public boolean existsByParking(Parking parking);
    public List<ParkingLot> findAllByParking(Parking parking);
}
