package com.parking.JustPark.service;

import com.parking.JustPark.exception.JustParkException;
import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.model.constant.Role;
import com.parking.JustPark.model.dto.UpdateUserDto;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.UserRepository;
import com.parking.JustPark.service.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /*----------ADMIN FUNCTIONS---------*/

    private void checkIfExistsAndNotAdmin(Long id) {
        User user = getUserById(id);
        if (user == null)
            throw new JustParkException("User not found", HttpStatus.BAD_REQUEST);
        if (user.getRoles().contains(Role.ADMIN))
            throw new JustParkException("Cannot perform action on admin", HttpStatus.BAD_REQUEST);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User banUser(Long id) {
        checkIfExistsAndNotAdmin(id);
        User user = getUserById(id);
        user.setAccountStatus(AccountStatus.BANNED);
        userRepository.save(user);
        log.info("User {} has been banned", user.getId());
        return user;
    }

    public User unbanUser(Long id) {
        checkIfExistsAndNotAdmin(id);
        User user = getUserById(id);
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);
        log.info("User {} has been unbanned", user.getId());
        return user;
    }

}
