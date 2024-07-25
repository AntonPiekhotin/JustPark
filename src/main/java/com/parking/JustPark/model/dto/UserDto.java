package com.parking.JustPark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class UserDto {

    String email;

    String phoneNumber;

    String firstName;

    String lastName;

    Date dateOfBirth;

    LocalDate registrationDate;

    String country;
}
