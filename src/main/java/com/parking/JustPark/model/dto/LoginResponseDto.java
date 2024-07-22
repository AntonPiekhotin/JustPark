package com.parking.JustPark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class LoginResponseDto {

    String token;

    Date expiresIn;

}
