package com.parking.JustPark.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginUserDto {

    @Size(max = 50)
    @Email(regexp = ".+@.+\\..+", message = "Email should be valid")
    @NotBlank(message = "Email should not be null or blank")
    String email;

    @NotBlank(message = "Password should not be null or blank")
    String password;
}
