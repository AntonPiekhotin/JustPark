package com.parking.JustPark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class UserDto {

    String email;

    String phoneNumber;

    String firstName;

    String lastName;

    LocalDate dateOfBirth;

    LocalDate registrationDate;

    String country;
}
