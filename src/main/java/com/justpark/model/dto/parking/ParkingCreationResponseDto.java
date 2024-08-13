package com.justpark.model.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ParkingCreationResponseDto {

    Long id;

    String title;

    String address;

    String city;

    BigDecimal pricePerHour;

}
