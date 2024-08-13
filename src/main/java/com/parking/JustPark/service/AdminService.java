package com.parking.JustPark.service;

import com.parking.JustPark.exception.JustParkException;
import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.model.constant.Role;
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

    public void deleteUser(Long id) {
        checkIfExistsAndNotAdmin(id);
        userRepository.deleteById(id);
        log.info("User {} has been deleted", id);
    }


    //*----------PARKING FUNCTIONS---------*/

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public Parking getParkingById(Long id) {
        return parkingRepository.findById(id).orElse(null);
    }
}
