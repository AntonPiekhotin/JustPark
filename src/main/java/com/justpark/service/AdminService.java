package com.justpark.service;

import com.justpark.exception.JustParkException;
import com.justpark.model.constant.AccountStatus;
import com.justpark.model.constant.Role;
import com.justpark.model.dto.parking.ParkingDto;
import com.justpark.model.dto.user.UserDto;
import com.justpark.model.entity.Parking;
import com.justpark.model.entity.User;
import com.justpark.repository.ParkingRepository;
import com.justpark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;

    /*----------USER FUNCTIONS---------*/

    private void checkIfExistsAndNotAdmin(Long id) {
        User user = getUserById(id);
        if (user == null)
            throw new JustParkException("User not found", HttpStatus.BAD_REQUEST);
        if (user.getRoles().contains(Role.ADMIN))
            throw new JustParkException("Cannot perform action on admin", HttpStatus.BAD_REQUEST);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dateOfBirth(user.getDateOfBirth())
                        .phoneNumber(user.getPhoneNumber())
                        .registrationDate(user.getRegistrationDate())
                        .country(user.getCountry())
                        .roles(user.getRoles())
                        .accountStatus(user.getAccountStatus())
                        .build())
                .toList();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new JustParkException("User not found", HttpStatus.BAD_REQUEST);
        return user;
    }

    public UserDto banUser(Long id) {
        checkIfExistsAndNotAdmin(id);
        User user = getUserById(id);
        user.setAccountStatus(AccountStatus.BANNED);
        userRepository.save(user);
        log.info("User {} has been banned", user.getId());
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .registrationDate(user.getRegistrationDate())
                .country(user.getCountry())
                .roles(user.getRoles())
                .accountStatus(user.getAccountStatus())
                .build();
    }

    public UserDto unbanUser(Long id) {
        checkIfExistsAndNotAdmin(id);
        User user = getUserById(id);
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);
        log.info("User {} has been unbanned", user.getId());
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .registrationDate(user.getRegistrationDate())
                .country(user.getCountry())
                .roles(user.getRoles())
                .accountStatus(user.getAccountStatus())
                .build();
    }

    public void deleteUser(Long id) {
        checkIfExistsAndNotAdmin(id);
        userRepository.deleteById(id);
        log.info("User {} has been deleted", id);
    }

    public List<ParkingDto> getUserParkings(Long id) {
        checkIfExistsAndNotAdmin(id);
        return parkingRepository.findAllByOwnerId(id).stream()
                .map(parking -> ParkingDto.builder()
                        .id(parking.getId())
                        .ownerId(parking.getOwner().getId())
                        .title(parking.getTitle())
                        .address(parking.getAddress())
                        .city(parking.getCity())
                        .pricePerHour(parking.getPricePerHour())
                        .build())
                .toList();
    }

    //*----------PARKING FUNCTIONS---------*/

    public List<ParkingDto> getAllParkings() {
        return parkingRepository.findAll().stream()
                .map(parking -> ParkingDto.builder()
                        .id(parking.getId())
                        .ownerId(parking.getOwner().getId())
                        .title(parking.getTitle())
                        .address(parking.getAddress())
                        .city(parking.getCity())
                        .pricePerHour(parking.getPricePerHour())
                        .build())
                .toList();
    }

    public ParkingDto getParkingById(Long id) {
        Parking parking = parkingRepository.findById(id).orElse(null);
        if (parking == null)
            throw new JustParkException("Parking not found", HttpStatus.BAD_REQUEST);
        return ParkingDto.builder()
                .id(parking.getId())
                .ownerId(parking.getOwner().getId())
                .title(parking.getTitle())
                .address(parking.getAddress())
                .city(parking.getCity())
                .pricePerHour(parking.getPricePerHour())
                .build();
    }

    public void deleteParking(Long id) {
        if (parkingRepository.existsById(id)) {
            parkingRepository.deleteById(id);
            log.info("Parking {} has been deleted", id);
            return;
        }
        throw new JustParkException("Parking not found", HttpStatus.BAD_REQUEST);
    }
}
