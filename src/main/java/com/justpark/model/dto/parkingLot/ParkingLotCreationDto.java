package com.justpark.model.dto.parkingLot;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ParkingLotCreationDto {

    @NotNull(message = "Title cannot be null")
    String title;

    @NotNull(message = "Layer cannot be null")
    Integer layer;
}
