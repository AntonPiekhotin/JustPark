package com.parking.JustPark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class ParkingDto {
    Long id;
    Long ownerId;
    String title;
    String address;
    String city;
    BigDecimal pricePerHour;
}
