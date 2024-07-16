package com.parking.JustPark.repository;

import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    public List<Parking> findAllByOwner(User owner);

    public boolean existsByOwner(User owner);

    public Optional<List<Parking>> findAllByCity(String city);
}
