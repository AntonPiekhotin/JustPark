package com.parking.JustPark.repository;

import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.ParkingRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRatingRepository extends JpaRepository<ParkingRating, Long> {
    public List<ParkingRating> findAllByParking(Parking parking);
    public boolean existsByParking(Parking parking);
}
