package com.justpark.repository;

import com.justpark.model.entity.Parking;
import com.justpark.model.entity.ParkingRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRatingRepository extends JpaRepository<ParkingRating, Long> {
    List<ParkingRating> findAllByParking(Parking parking);
    boolean existsByParking(Parking parking);
}
