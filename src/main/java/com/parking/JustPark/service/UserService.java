package com.parking.JustPark.service;


import com.parking.JustPark.entity.User;
import com.parking.JustPark.entity.enums.AccountStatus;
import com.parking.JustPark.entity.enums.Role;
import com.parking.JustPark.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void changeUserRoles(Long id, Map<String, String> roles) {
        User user = userRepository.findById(id).orElse(null);

        Set<String> rolesSet = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        if (user != null) {
            user.getRoles().clear();
            for (String key : roles.keySet()) {
                if (rolesSet.contains(key))
                    user.getRoles().add(Role.valueOf(key));
                userRepository.save(user);
                log.info("Roles edited on user {}", user.getId());
                return;
            }
        }
        log.info("Error occurred while editing roles of user {}", id);
    }


}
