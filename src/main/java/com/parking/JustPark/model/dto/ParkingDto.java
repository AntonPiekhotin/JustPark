package com.parking.JustPark.model.dto;

import com.parking.JustPark.model.entity.ParkingLot;
import com.parking.JustPark.model.entity.ParkingRating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class ParkingDto {
    Long id;
    String title;
    String address;
    String city;
    List<ParkingLot> parkingLots;
    List<ParkingRating> ratings;
    BigDecimal pricePerHour;
}
