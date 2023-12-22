package com.parking.JustPark.repository;

import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    public List<Parking> findAllByOwner(User owner);
    public boolean existsByOwner(User owner);
}
