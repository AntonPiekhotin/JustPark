package com.justpark.model.request;

import com.justpark.model.dto.parkingLot.ParkingLotCreationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class ParkingLotCreationRequest {

    @NotEmpty(message = "Request cannot be empty")
    @Valid
    List<ParkingLotCreationDto> parkingLots;

}
