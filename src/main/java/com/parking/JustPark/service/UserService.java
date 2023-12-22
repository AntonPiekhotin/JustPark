package com.parking.JustPark.service;


import com.parking.JustPark.entity.User;
import com.parking.JustPark.entity.enums.AccountStatus;
import com.parking.JustPark.entity.enums.Role;
import com.parking.JustPark.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null)
            return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.USER));

        user.setRegistrationDate(LocalDate.now());
        user.setAccountStatus(AccountStatus.ACTIVE.name());
        log.info("Saving new User with email {}", email);
        userRepository.save(user);
        return true;
    }
}
