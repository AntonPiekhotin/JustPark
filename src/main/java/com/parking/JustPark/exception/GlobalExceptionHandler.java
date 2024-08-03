package com.parking.JustPark.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ResponseErrorDto> handleUserNotFoundException(UsernameNotFoundException ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(List.of(ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .errorMessage(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ResponseErrorDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.CONFLICT.value()))
                .errorMessage(List.of(ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .errorMessage(List.of("Wrong password"))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
    }

    @ExceptionHandler(value = NoAccessException.class)
    public ResponseEntity<ResponseErrorDto> handleNoAccessException(NoAccessException ex) {
        ResponseErrorDto errorDto = ResponseErrorDto.builder()
                .time(LocalDateTime.now())
                .statusCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .errorMessage(List.of(ex.getMessage()))
                .stackTrace(List.of(Arrays.toString(ex.getStackTrace())))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDto);
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
