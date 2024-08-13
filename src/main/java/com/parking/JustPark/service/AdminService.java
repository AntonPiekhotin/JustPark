package com.parking.JustPark.service;

import com.parking.JustPark.exception.JustParkException;
import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.model.constant.Role;
import com.parking.JustPark.model.dto.ParkingDto;
import com.parking.JustPark.model.dto.UserDto;
import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.repository.ParkingRepository;
import com.parking.JustPark.repository.UserRepository;
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
