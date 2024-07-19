package com.parking.JustPark.service;


import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.model.constant.Role;
import com.parking.JustPark.repository.ParkingRepository;
import com.parking.JustPark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ParkingRepository parkingRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email).isPresent())
            return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.USER));

        user.setRegistrationDate(LocalDate.now());
        user.setAccountStatus(AccountStatus.ACTIVE);
        log.info("Saving new User with email {}", email);
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAllByOrderById();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<Parking> parkingList(Long ownerId) {
        User owner = userRepository.findById(ownerId).orElse(null);
        if (owner == null) {
            log.info("User with this id {} doesn`t exists", ownerId);
            return null;
        }
        return parkingRepository.findAllByOwner(owner);
    }

    public boolean editStatus(Long userId, AccountStatus newStatus) {
        User user = userRepository.findById(userId).orElse(null);
        if (newStatus == null) {
            log.info("Error occurred while editing user status. Passed new status is null");
            return false;
        }
        if (user == null) {
            log.info("Error occurred while editing user status. Passed user is null");
            return false;
        }
        user.setAccountStatus(newStatus);
        userRepository.save(user);
        log.info("Status edited on user {}", userId);
        return true;
    }

    /**
     * Змінює статус акаунта даного користувача на DELETED. Не видаляє користувача з бази даних.
     *
     * @param userId ідентифікатор користувача.
     * @return true якщо операція успішна, false якщо операція не успішна.
     */
    public boolean deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setAccountStatus(AccountStatus.DELETED);
            userRepository.save(user);
            log.info("User {} status has been changed to DELETED", userId);
            return true;
        }
        log.info("Error occurred while changing account status on user {}", userId);
        return false;
    }

    /**
     * Повністю видаляє користувача з бази даних.
     *
     * @param userId ідентифікатор користувача.
     * @return true якщо операція успішна, false якщо операція не успішна.
     */
    public boolean totalDeleteUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            log.info("User {} has been totally deleted", userId);
            return true;
        }
        log.info("Error occurred while deleting user {}", userId);
        return false;
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();
        return userRepository.findByEmail(principal.getName()).get();
    }

    /**
     * Змінює список ролей наданого користувача.
     *
     * @param userId ідентифікатор користувача.
     * @param roles  список нових ролей.
     */
    public void changeUserRoles(Long userId, Map<String, String> roles) {
        User user = userRepository.findById(userId).orElse(null);
        Set<String> rolesSet = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        if (user != null) {
            user.getRoles().clear();
            for (String key : roles.keySet()) {
                if (rolesSet.contains(key))
                    user.getRoles().add(Role.valueOf(key));
            }
            userRepository.save(user);
            log.info("Roles edited on user {}", user.getId());
            return;
        }
        log.info("Error occurred while editing roles of user {}", userId);
    }

    public boolean editUser(User user) {
        if (user != null) {
            userRepository.save(user);
            log.info("User {} has been edited", user.getId());
            return true;
        }
        log.info("Error occurred while editing info in user {}, probably passed object is null", user.getId());
        return false;
    }

    public boolean banUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getAccountStatus() == AccountStatus.BANNED) {
            log.info("Error occurred while baning user with id {}, probably id is wrong or user is already banned",
                    userId);
            return false;
        }
        user.setAccountStatus(AccountStatus.BANNED);
        userRepository.save(user);
        log.info("User {} has been banned", userId);
        return true;
    }

    public boolean unbanUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getAccountStatus() != AccountStatus.BANNED) {
            log.info("Error occurred while baning user with id {}, probably id is wrong or user is not banned",
                    userId);
            return false;
        }
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);
        log.info("User {} has been unbanned", userId);
        return true;
    }
}
