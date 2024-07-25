package com.parking.JustPark.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class RegisterUserDto {

    @Size(max = 50)
    @Email(regexp = ".+@.+\\..+", message = "Email should be valid")
    @NotBlank(message = "Email should be valid and not blank")
    String email;

    @Size(max = 500)
    @NotBlank(message = "Password should be valid and not blank")
    String password;

    @Size(max = 50)
    @NotBlank(message = "First name should be valid and not blank")
    String firstName;

    String lastName;

    String phoneNumber;

    String country;

}
