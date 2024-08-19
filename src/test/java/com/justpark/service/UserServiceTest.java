package com.justpark.service;

import com.justpark.model.constant.AccountStatus;
import com.justpark.model.dto.user.UpdateUserDto;
import com.justpark.model.entity.User;
import com.justpark.repository.UserRepository;
import com.justpark.service.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#getAuthenticatedUser(String)}
     */
    @Test
    void testGetAuthenticatedUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(LocalDate.of(1970, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");

        // Act
        User actualAuthenticatedUser = userService.getAuthenticatedUser("com.justpark.service.UserService");

        // Assert
        verify(userRepository).findByEmail(eq("janedoe"));
        verify(jwtService).extractUsername(eq("tpark.service.UserService"));
        assertSame(user, actualAuthenticatedUser);
    }

    /**
     * Method under test: {@link UserService#getUserByEmail(String)}
     */
    @Test
    void testGetUserByEmail() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(LocalDate.of(1970, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        User actualUserByEmail = userService.getUserByEmail("jane.doe@example.org");

        // Assert
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        assertSame(user, actualUserByEmail);
    }

    /**
     * Method under test: {@link UserService#updateUser(User, UpdateUserDto)}
     */
    @Test
    void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(LocalDate.of(1970, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkings(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());

        // Act
        User actualUpdateUserResult = userService.updateUser(user2,
                new UpdateUserDto("6625550144", "Jane", "Doe", LocalDate.of(1970, 1, 1), "GB"));

        // Assert
        verify(userRepository).save(isA(User.class));
        assertSame(user, actualUpdateUserResult);
    }

    /**
     * Method under test: {@link UserService#deleteUser(User)}
     */
    @Test
    void testDeleteUser() {
        // Arrange
        doNothing().when(userRepository).delete(Mockito.<User>any());

        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(LocalDate.of(1970, 1, 1));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());

        // Act
        userService.deleteUser(user);

        // Assert that nothing has changed
        verify(userRepository).delete(isA(User.class));
    }
}
