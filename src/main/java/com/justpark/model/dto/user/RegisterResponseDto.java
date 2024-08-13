package com.justpark.model.dto.user;

import com.justpark.model.constant.AccountStatus;
import com.justpark.model.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RegisterResponseDto {

    String email;

    Set<Role> roles;

    String phoneNumber;

    String firstName;

    String lastName;

    LocalDate registrationDate;

    String country;

    AccountStatus accountStatus;
}
