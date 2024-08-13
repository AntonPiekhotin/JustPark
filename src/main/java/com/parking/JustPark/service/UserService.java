package com.parking.JustPark.service;

import com.parking.JustPark.model.dto.UpdateUserDto;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.UserRepository;
import com.parking.JustPark.service.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User getAuthenticatedUser(String token) {
        String currentUserEmail = jwtService.extractUsername(token.substring(7));
        return getUserByEmail(currentUserEmail);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User updateUser(User user, UpdateUserDto updateUserDto) {
        user.setPhoneNumber(updateUserDto.getPhoneNumber());
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setDateOfBirth(updateUserDto.getDateOfBirth());
        user.setCountry(updateUserDto.getCountry());
        log.info("User {} has been updated", user.getId());
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
        log.info("User {} has been deleted", user.getId());
    }

}
