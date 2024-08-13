package com.justpark.model.dto.parking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class UpdateParkingDto {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title is too long")
    String title;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address is too long")
    String address;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City is too long")
    String city;

    BigDecimal pricePerHour;
}
