package com.parking.JustPark.service.security.jwt;

import com.parking.JustPark.exception.JustParkException;
import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.model.constant.Role;
import com.parking.JustPark.model.dto.LoginUserDto;
import com.parking.JustPark.model.dto.RegisterUserDto;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new JustParkException("User with provided email already exists", HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .roles(Set.of(Role.USER))
                .phoneNumber(input.getPhoneNumber())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .registrationDate(LocalDate.now())
                .country(input.getCountry())
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with provided email not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return user;
    }
}