package com.parking.JustPark.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ResponseErrorDto> handleInvalidJwtTokenException(SignatureException ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(List.of("Invalid JWT token: " + ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    protected ResponseEntity<ResponseErrorDto> handlerExpiredJwtException(ExpiredJwtException ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(List.of("JWT token expired: ", ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ResponseErrorDto> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .errorMessage(List.of(ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDto);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> handleUserNotFoundException(Exception ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(List.of(ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseErrorDto> handleException(Exception ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .errorMessage(List.of(ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }
}
