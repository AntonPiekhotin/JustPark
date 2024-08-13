package com.justpark.service;

import com.justpark.model.dto.user.UpdateUserDto;
import com.justpark.model.entity.User;
import com.justpark.repository.UserRepository;
import com.justpark.service.security.jwt.JwtService;
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
