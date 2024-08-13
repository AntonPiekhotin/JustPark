package com.parking.JustPark.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class LoginResponseDto {

    String token;

    Date expiresIn;

}
