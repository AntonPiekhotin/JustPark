package com.justpark.model.dto.parking;

import com.justpark.model.entity.ParkingLot;
import com.justpark.model.entity.ParkingRating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ParkingResponseDto {

    String title;
    String address;
    String city;
    BigDecimal pricePerHour;
    List<ParkingLot> parkingLots;
    List<ParkingRating> ratings;
}
