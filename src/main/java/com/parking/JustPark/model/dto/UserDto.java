package com.parking.JustPark.model.dto;

import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.model.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class UserDto {

    Long id;

    String email;

    String phoneNumber;

    String firstName;

    String lastName;

    LocalDate dateOfBirth;

    LocalDate registrationDate;

    String country;

    Set<Role> roles;

    AccountStatus accountStatus;
}
