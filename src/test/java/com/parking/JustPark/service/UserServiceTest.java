package com.parking.JustPark.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.repository.ParkingRepository;
import com.parking.JustPark.repository.UserRepository;
import com.sun.security.auth.UserPrincipal;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private ParkingRepository parkingRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(Optional.of(user));

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());

        // Act
        boolean actualCreateUserResult = userService.createUser(user2);

        // Assert
        verify(userRepository).findByEmail(Mockito.<String>any());
        assertFalse(actualCreateUserResult);
    }

    /**
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        // Arrange
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAllByOrderById()).thenReturn(userList);

        // Act
        List<User> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(userRepository).findAllByOrderById();
        assertTrue(actualAllUsers.isEmpty());
        assertSame(userList, actualAllUsers);
    }

    /**
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    void testGetUserById() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        User actualUserById = userService.getUserById(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertSame(user, actualUserById);
    }

    /**
     * Method under test: {@link UserService#parkingList(Long)}
     */
    @Test
    void testParkingList() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ArrayList<Parking> parkingList = new ArrayList<>();
        when(parkingRepository.findAllByOwner(Mockito.<User>any())).thenReturn(parkingList);

        // Act
        List<Parking> actualParkingListResult = userService.parkingList(1L);

        // Assert
        verify(parkingRepository).findAllByOwner(Mockito.<User>any());
        verify(userRepository).findById(Mockito.<Long>any());
        assertTrue(actualParkingListResult.isEmpty());
        assertSame(parkingList, actualParkingListResult);
    }

    /**
     * Method under test: {@link UserService#parkingList(Long)}
     */
    @Test
    void testParkingList2() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        List<Parking> actualParkingListResult = userService.parkingList(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertNull(actualParkingListResult);
    }

    /**
     * Method under test: {@link UserService#editStatus(Long, AccountStatus)}
     */
    @Test
    void testEditStatus() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualEditStatusResult = userService.editStatus(1L, AccountStatus.ACTIVE);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualEditStatusResult);
    }

    /**
     * Method under test: {@link UserService#editStatus(Long, AccountStatus)}
     */
    @Test
    void testEditStatus2() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        boolean actualEditStatusResult = userService.editStatus(1L, AccountStatus.ACTIVE);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualEditStatusResult);
    }

    /**
     * Method under test: {@link UserService#editStatus(Long, AccountStatus)}
     */
    @Test
    void testEditStatus3() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualEditStatusResult = userService.editStatus(1L, null);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualEditStatusResult);
    }

    /**
     * Method under test: {@link UserService#editStatus(Long, AccountStatus)}
     */
    @Test
    void testEditStatus4() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualEditStatusResult = userService.editStatus(1L, AccountStatus.INACTIVE);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualEditStatusResult);
    }

    /**
     * Method under test: {@link UserService#editStatus(Long, AccountStatus)}
     */
    @Test
    void testEditStatus5() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualEditStatusResult = userService.editStatus(1L, AccountStatus.BANNED);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualEditStatusResult);
    }

    /**
     * Method under test: {@link UserService#editStatus(Long, AccountStatus)}
     */
    @Test
    void testEditStatus6() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualEditStatusResult = userService.editStatus(1L, AccountStatus.DELETED);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualEditStatusResult);
    }

    /**
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualDeleteUserResult = userService.deleteUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualDeleteUserResult);
    }

    /**
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser2() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        boolean actualDeleteUserResult = userService.deleteUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualDeleteUserResult);
    }

    /**
     * Method under test: {@link UserService#totalDeleteUser(Long)}
     */
    @Test
    void testTotalDeleteUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualTotalDeleteUserResult = userService.totalDeleteUser(1L);

        // Assert
        verify(userRepository).delete(Mockito.<User>any());
        verify(userRepository).findById(Mockito.<Long>any());
        assertTrue(actualTotalDeleteUserResult);
    }

    /**
     * Method under test: {@link UserService#totalDeleteUser(Long)}
     */
    @Test
    void testTotalDeleteUser2() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        boolean actualTotalDeleteUserResult = userService.totalDeleteUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualTotalDeleteUserResult);
    }

    /**
     * Method under test: {@link UserService#changeUserRoles(Long, Map)}
     */
    @Test
    void testChangeUserRoles() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        userService.changeUserRoles(1L, new HashMap<>());

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
    }

    /**
     * Method under test: {@link UserService#changeUserRoles(Long, Map)}
     */
    @Test
    void testChangeUserRoles2() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        userService.changeUserRoles(1L, new HashMap<>());

        // Assert that nothing has changed
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link UserService#changeUserRoles(Long, Map)}
     */
    @Test
    void testChangeUserRoles3() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        HashMap<String, String> roles = new HashMap<>();
        roles.put("Roles edited on user {}", "Roles edited on user {}");

        // Act
        userService.changeUserRoles(1L, roles);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
    }

    /**
     * Method under test: {@link UserService#editUser(User)}
     */
    @Test
    void testEditUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());

        // Act
        boolean actualEditUserResult = userService.editUser(user2);

        // Assert
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualEditUserResult);
    }

    /**
     * Method under test: {@link UserService#banUser(Long)}
     */
    @Test
    void testBanUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualBanUserResult = userService.banUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualBanUserResult);
    }

    /**
     * Method under test: {@link UserService#banUser(Long)}
     */
    @Test
    void testBanUser2() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.BANNED);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualBanUserResult = userService.banUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualBanUserResult);
    }

    /**
     * Method under test: {@link UserService#banUser(Long)}
     */
    @Test
    void testBanUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        boolean actualBanUserResult = userService.banUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualBanUserResult);
    }

    /**
     * Method under test: {@link UserService#unbanUser(Long)}
     */
    @Test
    void testUnbanUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualUnbanUserResult = userService.unbanUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualUnbanUserResult);
    }

    /**
     * Method under test: {@link UserService#unbanUser(Long)}
     */
    @Test
    void testUnbanUser2() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.BANNED);
        user.setCountry("GB");
        user.setDateOfBirth(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.ACTIVE);
        user2.setCountry("GB");
        user2.setDateOfBirth(mock(Date.class));
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(1L);
        user2.setLastName("Doe");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("iloveyou");
        user2.setPhoneNumber("6625550144");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualUnbanUserResult = userService.unbanUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertTrue(actualUnbanUserResult);
    }

    /**
     * Method under test: {@link UserService#unbanUser(Long)}
     */
    @Test
    void testUnbanUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        boolean actualUnbanUserResult = userService.unbanUser(1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualUnbanUserResult);
    }
}
