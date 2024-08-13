package com.parking.JustPark.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class UpdateUserDto {

    @NotBlank(message = "phone number is mandatory")
    String phoneNumber;

    @NotBlank(message = "first name is mandatory")
    String firstName;

    @NotBlank(message = "last name is mandatory")
    String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;

    @NotBlank(message = "country is mandatory")
    String country;
}
