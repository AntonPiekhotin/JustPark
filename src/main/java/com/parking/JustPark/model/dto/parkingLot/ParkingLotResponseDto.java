package com.parking.JustPark.model.dto.parkingLot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class ParkingLotResponseDto {
    Long id;
    Long parkingId;
    String title;
    Integer layer;
    Boolean isEmpty;
}
