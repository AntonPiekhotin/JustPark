package com.justpark.service;

import com.justpark.exception.JustParkException;
import com.justpark.model.constant.AccountStatus;
import com.justpark.model.constant.Role;
import com.justpark.model.dto.parking.ParkingDto;
import com.justpark.model.dto.user.UserDto;
import com.justpark.model.entity.Parking;
import com.justpark.model.entity.ParkingLot;
import com.justpark.model.entity.ParkingRating;
import com.justpark.model.entity.User;
import com.justpark.repository.ParkingRepository;
import com.justpark.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AdminService.class})
@ExtendWith(SpringExtension.class)
class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @MockBean
    private ParkingRepository parkingRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link AdminService#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<UserDto> actualAllUsers = adminService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isEmpty());
    }

    /**
     * Method under test: {@link AdminService#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        LocalDate registrationDate = LocalDate.of(1970, 1, 1);
        user.setRegistrationDate(registrationDate);
        HashSet<Role> roles = new HashSet<>();
        user.setRoles(roles);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> actualAllUsers = adminService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertEquals(1, actualAllUsers.size());
        UserDto getResult = actualAllUsers.get(0);
        LocalDate dateOfBirth2 = getResult.getDateOfBirth();
        assertEquals("1970-01-01", dateOfBirth2.toString());
        LocalDate registrationDate2 = getResult.getRegistrationDate();
        assertEquals("1970-01-01", registrationDate2.toString());
        assertEquals("6625550144", getResult.getPhoneNumber());
        assertEquals("Doe", getResult.getLastName());
        assertEquals("GB", getResult.getCountry());
        assertEquals("Jane", getResult.getFirstName());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(AccountStatus.ACTIVE, getResult.getAccountStatus());
        Set<Role> roles2 = getResult.getRoles();
        assertTrue(roles2.isEmpty());
        assertSame(roles, roles2);
        assertSame(dateOfBirth, dateOfBirth2);
        assertSame(registrationDate, registrationDate2);
    }

    /**
     * Method under test: {@link AdminService#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        LocalDate registrationDate = LocalDate.of(1970, 1, 1);
        user.setRegistrationDate(registrationDate);
        HashSet<Role> roles = new HashSet<>();
        user.setRoles(roles);

        User user2 = new User();
        user2.setAccountStatus(AccountStatus.INACTIVE);
        user2.setCountry("GBR");
        LocalDate dateOfBirth2 = LocalDate.of(1970, 1, 1);
        user2.setDateOfBirth(dateOfBirth2);
        user2.setEmail("john.smith@example.org");
        user2.setFirstName("John");
        user2.setId(2L);
        user2.setLastName("Smith");
        user2.setParkings(new ArrayList<>());
        user2.setPassword("Password");
        user2.setPhoneNumber("8605550118");
        LocalDate registrationDate2 = LocalDate.of(1970, 1, 1);
        user2.setRegistrationDate(registrationDate2);
        HashSet<Role> roles2 = new HashSet<>();
        user2.setRoles(roles2);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> actualAllUsers = adminService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertEquals(2, actualAllUsers.size());
        UserDto getResult = actualAllUsers.get(0);
        LocalDate dateOfBirth3 = getResult.getDateOfBirth();
        assertEquals("1970-01-01", dateOfBirth3.toString());
        UserDto getResult2 = actualAllUsers.get(1);
        LocalDate dateOfBirth4 = getResult2.getDateOfBirth();
        assertEquals("1970-01-01", dateOfBirth4.toString());
        LocalDate registrationDate3 = getResult.getRegistrationDate();
        assertEquals("1970-01-01", registrationDate3.toString());
        LocalDate registrationDate4 = getResult2.getRegistrationDate();
        assertEquals("1970-01-01", registrationDate4.toString());
        assertEquals("6625550144", getResult2.getPhoneNumber());
        assertEquals("8605550118", getResult.getPhoneNumber());
        assertEquals("Doe", getResult2.getLastName());
        assertEquals("GB", getResult2.getCountry());
        assertEquals("GBR", getResult.getCountry());
        assertEquals("Jane", getResult2.getFirstName());
        assertEquals("John", getResult.getFirstName());
        assertEquals("Smith", getResult.getLastName());
        assertEquals("jane.doe@example.org", getResult2.getEmail());
        assertEquals("john.smith@example.org", getResult.getEmail());
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals(2L, getResult.getId().longValue());
        assertEquals(AccountStatus.ACTIVE, getResult2.getAccountStatus());
        assertEquals(AccountStatus.INACTIVE, getResult.getAccountStatus());
        Set<Role> roles3 = getResult.getRoles();
        assertTrue(roles3.isEmpty());
        Set<Role> roles4 = getResult2.getRoles();
        assertTrue(roles4.isEmpty());
        assertSame(roles2, roles3);
        assertSame(roles, roles4);
        assertSame(dateOfBirth2, dateOfBirth3);
        assertSame(dateOfBirth, dateOfBirth4);
        assertSame(registrationDate2, registrationDate3);
        assertSame(registrationDate, registrationDate4);
    }

    /**
     * Method under test: {@link AdminService#getAllUsers()}
     */
    @Test
    void testGetAllUsers4() {
        // Arrange
        when(userRepository.findAll()).thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getAllUsers());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link AdminService#getUserById(Long)}
     */
    @Test
    void testGetUserById() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        User actualUserById = adminService.getUserById(1L);

        // Assert
        verify(userRepository).findById(eq(1L));
        assertSame(user, actualUserById);
    }

    /**
     * Method under test: {@link AdminService#getUserById(Long)}
     */
    @Test
    void testGetUserById2() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getUserById(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#getUserById(Long)}
     */
    @Test
    void testGetUserById3() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getUserById(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#banUser(Long)}
     */
    @Test
    void testBanUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        LocalDate registrationDate = LocalDate.of(1970, 1, 1);
        user.setRegistrationDate(registrationDate);
        HashSet<Role> roles = new HashSet<>();
        user.setRoles(roles);
        Optional<User> ofResult = Optional.of(user);

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
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        UserDto actualBanUserResult = adminService.banUser(1L);

        // Assert
        verify(userRepository, atLeast(1)).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
        LocalDate dateOfBirth2 = actualBanUserResult.getDateOfBirth();
        assertEquals("1970-01-01", dateOfBirth2.toString());
        LocalDate registrationDate2 = actualBanUserResult.getRegistrationDate();
        assertEquals("1970-01-01", registrationDate2.toString());
        assertEquals("6625550144", actualBanUserResult.getPhoneNumber());
        assertEquals("Doe", actualBanUserResult.getLastName());
        assertEquals("GB", actualBanUserResult.getCountry());
        assertEquals("Jane", actualBanUserResult.getFirstName());
        assertEquals("jane.doe@example.org", actualBanUserResult.getEmail());
        assertEquals(1L, actualBanUserResult.getId().longValue());
        assertEquals(AccountStatus.BANNED, actualBanUserResult.getAccountStatus());
        Set<Role> roles2 = actualBanUserResult.getRoles();
        assertTrue(roles2.isEmpty());
        assertSame(roles, roles2);
        assertSame(dateOfBirth, dateOfBirth2);
        assertSame(registrationDate, registrationDate2);
    }

    /**
     * Method under test: {@link AdminService#banUser(Long)}
     */
    @Test
    void testBanUser2() {
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
        when(userRepository.save(Mockito.<User>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.banUser(1L));
        verify(userRepository, atLeast(1)).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link AdminService#banUser(Long)}
     */
    @Test
    void testBanUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.banUser(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#unbanUser(Long)}
     */
    @Test
    void testUnbanUser() {
        // Arrange
        User user = new User();
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setCountry("GB");
        LocalDate dateOfBirth = LocalDate.of(1970, 1, 1);
        user.setDateOfBirth(dateOfBirth);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(1L);
        user.setLastName("Doe");
        user.setParkings(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        LocalDate registrationDate = LocalDate.of(1970, 1, 1);
        user.setRegistrationDate(registrationDate);
        HashSet<Role> roles = new HashSet<>();
        user.setRoles(roles);
        Optional<User> ofResult = Optional.of(user);

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
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        UserDto actualUnbanUserResult = adminService.unbanUser(1L);

        // Assert
        verify(userRepository, atLeast(1)).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
        LocalDate dateOfBirth2 = actualUnbanUserResult.getDateOfBirth();
        assertEquals("1970-01-01", dateOfBirth2.toString());
        LocalDate registrationDate2 = actualUnbanUserResult.getRegistrationDate();
        assertEquals("1970-01-01", registrationDate2.toString());
        assertEquals("6625550144", actualUnbanUserResult.getPhoneNumber());
        assertEquals("Doe", actualUnbanUserResult.getLastName());
        assertEquals("GB", actualUnbanUserResult.getCountry());
        assertEquals("Jane", actualUnbanUserResult.getFirstName());
        assertEquals("jane.doe@example.org", actualUnbanUserResult.getEmail());
        assertEquals(1L, actualUnbanUserResult.getId().longValue());
        assertEquals(AccountStatus.ACTIVE, actualUnbanUserResult.getAccountStatus());
        Set<Role> roles2 = actualUnbanUserResult.getRoles();
        assertTrue(roles2.isEmpty());
        assertSame(roles, roles2);
        assertSame(dateOfBirth, dateOfBirth2);
        assertSame(registrationDate, registrationDate2);
    }

    /**
     * Method under test: {@link AdminService#unbanUser(Long)}
     */
    @Test
    void testUnbanUser2() {
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
        when(userRepository.save(Mockito.<User>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.unbanUser(1L));
        verify(userRepository, atLeast(1)).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Method under test: {@link AdminService#unbanUser(Long)}
     */
    @Test
    void testUnbanUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.unbanUser(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser() {
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
        doNothing().when(userRepository).deleteById(Mockito.<Long>any());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        adminService.deleteUser(1L);

        // Assert that nothing has changed
        verify(userRepository).deleteById(eq(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser2() {
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
        doThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE)).when(userRepository)
                .deleteById(Mockito.<Long>any());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.deleteUser(1L));
        verify(userRepository).deleteById(eq(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.deleteUser(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser4() {
        // Arrange
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);

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
        user.setRoles(roles);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.deleteUser(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#getUserParkings(Long)}
     */
    @Test
    void testGetUserParkings() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(parkingRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        List<ParkingDto> actualUserParkings = adminService.getUserParkings(1L);

        // Assert
        verify(parkingRepository).findAllByOwnerId(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertTrue(actualUserParkings.isEmpty());
    }

    /**
     * Method under test: {@link AdminService#getUserParkings(Long)}
     */
    @Test
    void testGetUserParkings2() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(parkingRepository.findAllByOwnerId(Mockito.<Long>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getUserParkings(1L));
        verify(parkingRepository).findAllByOwnerId(eq(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#getUserParkings(Long)}
     */
    @Test
    void testGetUserParkings3() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getUserParkings(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#getUserParkings(Long)}
     */
    @Test
    void testGetUserParkings4() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkings(new ArrayList<>());
        owner.setPassword("iloveyou");
        owner.setPhoneNumber("6625550144");
        owner.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner.setRoles(new HashSet<>());

        Parking parking = new Parking();
        parking.setAddress("42 Main St");
        parking.setCity("Oxford");
        parking.setId(1L);
        parking.setOwner(owner);
        parking.setParkingLots(new ArrayList<>());
        BigDecimal pricePerHour = new BigDecimal("2.3");
        parking.setPricePerHour(pricePerHour);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");

        ArrayList<Parking> parkingList = new ArrayList<>();
        parkingList.add(parking);
        when(parkingRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(parkingList);

        // Act
        List<ParkingDto> actualUserParkings = adminService.getUserParkings(1L);

        // Assert
        verify(parkingRepository).findAllByOwnerId(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals(1, actualUserParkings.size());
        ParkingDto getResult = actualUserParkings.get(0);
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals("Dr", getResult.getTitle());
        assertEquals("Oxford", getResult.getCity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour2 = getResult.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour2);
        assertSame(pricePerHour, pricePerHour2);
    }

    /**
     * Method under test: {@link AdminService#getUserParkings(Long)}
     */
    @Test
    void testGetUserParkings5() {
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
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkings(new ArrayList<>());
        owner.setPassword("iloveyou");
        owner.setPhoneNumber("6625550144");
        owner.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner.setRoles(new HashSet<>());

        Parking parking = new Parking();
        parking.setAddress("42 Main St");
        parking.setCity("Oxford");
        parking.setId(1L);
        parking.setOwner(owner);
        parking.setParkingLots(new ArrayList<>());
        BigDecimal pricePerHour = new BigDecimal("2.3");
        parking.setPricePerHour(pricePerHour);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");

        User owner2 = new User();
        owner2.setAccountStatus(AccountStatus.INACTIVE);
        owner2.setCountry("GBR");
        owner2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner2.setEmail("john.smith@example.org");
        owner2.setFirstName("John");
        owner2.setId(2L);
        owner2.setLastName("Smith");
        owner2.setParkings(new ArrayList<>());
        owner2.setPassword("Password");
        owner2.setPhoneNumber("8605550118");
        owner2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner2.setRoles(new HashSet<>());

        Parking parking2 = new Parking();
        parking2.setAddress("17 High St");
        parking2.setCity("London");
        parking2.setId(2L);
        parking2.setOwner(owner2);
        parking2.setParkingLots(new ArrayList<>());
        BigDecimal pricePerHour2 = new BigDecimal("2.3");
        parking2.setPricePerHour(pricePerHour2);
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Mr");

        ArrayList<Parking> parkingList = new ArrayList<>();
        parkingList.add(parking2);
        parkingList.add(parking);
        when(parkingRepository.findAllByOwnerId(Mockito.<Long>any())).thenReturn(parkingList);

        // Act
        List<ParkingDto> actualUserParkings = adminService.getUserParkings(1L);

        // Assert
        verify(parkingRepository).findAllByOwnerId(eq(1L));
        verify(userRepository).findById(eq(1L));
        assertEquals(2, actualUserParkings.size());
        ParkingDto getResult = actualUserParkings.get(0);
        assertEquals("17 High St", getResult.getAddress());
        ParkingDto getResult2 = actualUserParkings.get(1);
        assertEquals("42 Main St", getResult2.getAddress());
        assertEquals("Dr", getResult2.getTitle());
        assertEquals("London", getResult.getCity());
        assertEquals("Mr", getResult.getTitle());
        assertEquals("Oxford", getResult2.getCity());
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals(1L, getResult2.getOwnerId().longValue());
        assertEquals(2L, getResult.getId().longValue());
        assertEquals(2L, getResult.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour3 = getResult.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour3);
        BigDecimal expectedPricePerHour2 = new BigDecimal("2.3");
        BigDecimal pricePerHour4 = getResult2.getPricePerHour();
        assertEquals(expectedPricePerHour2, pricePerHour4);
        assertSame(pricePerHour2, pricePerHour3);
        assertSame(pricePerHour, pricePerHour4);
    }

    /**
     * Method under test: {@link AdminService#getUserParkings(Long)}
     */
    @Test
    void testGetUserParkings6() {
        // Arrange
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);

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
        user.setRoles(roles);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getUserParkings(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#getAllParkings()}
     */
    @Test
    void testGetAllParkings() {
        // Arrange
        when(parkingRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<ParkingDto> actualAllParkings = adminService.getAllParkings();

        // Assert
        verify(parkingRepository).findAll();
        assertTrue(actualAllParkings.isEmpty());
    }

    /**
     * Method under test: {@link AdminService#getAllParkings()}
     */
    @Test
    void testGetAllParkings2() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkings(new ArrayList<>());
        owner.setPassword("iloveyou");
        owner.setPhoneNumber("6625550144");
        owner.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner.setRoles(new HashSet<>());

        Parking parking = new Parking();
        parking.setAddress("42 Main St");
        parking.setCity("Oxford");
        parking.setId(1L);
        parking.setOwner(owner);
        parking.setParkingLots(new ArrayList<>());
        BigDecimal pricePerHour = new BigDecimal("2.3");
        parking.setPricePerHour(pricePerHour);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");

        ArrayList<Parking> parkingList = new ArrayList<>();
        parkingList.add(parking);
        when(parkingRepository.findAll()).thenReturn(parkingList);

        // Act
        List<ParkingDto> actualAllParkings = adminService.getAllParkings();

        // Assert
        verify(parkingRepository).findAll();
        assertEquals(1, actualAllParkings.size());
        ParkingDto getResult = actualAllParkings.get(0);
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals("Dr", getResult.getTitle());
        assertEquals("Oxford", getResult.getCity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour2 = getResult.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour2);
        assertSame(pricePerHour, pricePerHour2);
    }

    /**
     * Method under test: {@link AdminService#getAllParkings()}
     */
    @Test
    void testGetAllParkings3() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkings(new ArrayList<>());
        owner.setPassword("iloveyou");
        owner.setPhoneNumber("6625550144");
        owner.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner.setRoles(new HashSet<>());

        Parking parking = new Parking();
        parking.setAddress("42 Main St");
        parking.setCity("Oxford");
        parking.setId(1L);
        parking.setOwner(owner);
        parking.setParkingLots(new ArrayList<>());
        BigDecimal pricePerHour = new BigDecimal("2.3");
        parking.setPricePerHour(pricePerHour);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");

        User owner2 = new User();
        owner2.setAccountStatus(AccountStatus.INACTIVE);
        owner2.setCountry("GBR");
        owner2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner2.setEmail("john.smith@example.org");
        owner2.setFirstName("John");
        owner2.setId(2L);
        owner2.setLastName("Smith");
        owner2.setParkings(new ArrayList<>());
        owner2.setPassword("Password");
        owner2.setPhoneNumber("8605550118");
        owner2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner2.setRoles(new HashSet<>());

        Parking parking2 = new Parking();
        parking2.setAddress("17 High St");
        parking2.setCity("London");
        parking2.setId(2L);
        parking2.setOwner(owner2);
        parking2.setParkingLots(new ArrayList<>());
        BigDecimal pricePerHour2 = new BigDecimal("2.3");
        parking2.setPricePerHour(pricePerHour2);
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Mr");

        ArrayList<Parking> parkingList = new ArrayList<>();
        parkingList.add(parking2);
        parkingList.add(parking);
        when(parkingRepository.findAll()).thenReturn(parkingList);

        // Act
        List<ParkingDto> actualAllParkings = adminService.getAllParkings();

        // Assert
        verify(parkingRepository).findAll();
        assertEquals(2, actualAllParkings.size());
        ParkingDto getResult = actualAllParkings.get(0);
        assertEquals("17 High St", getResult.getAddress());
        ParkingDto getResult2 = actualAllParkings.get(1);
        assertEquals("42 Main St", getResult2.getAddress());
        assertEquals("Dr", getResult2.getTitle());
        assertEquals("London", getResult.getCity());
        assertEquals("Mr", getResult.getTitle());
        assertEquals("Oxford", getResult2.getCity());
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals(1L, getResult2.getOwnerId().longValue());
        assertEquals(2L, getResult.getId().longValue());
        assertEquals(2L, getResult.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour3 = getResult.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour3);
        BigDecimal expectedPricePerHour2 = new BigDecimal("2.3");
        BigDecimal pricePerHour4 = getResult2.getPricePerHour();
        assertEquals(expectedPricePerHour2, pricePerHour4);
        assertSame(pricePerHour2, pricePerHour3);
        assertSame(pricePerHour, pricePerHour4);
    }

    /**
     * Method under test: {@link AdminService#getAllParkings()}
     */
    @Test
    void testGetAllParkings4() {
        // Arrange
        when(parkingRepository.findAll()).thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getAllParkings());
        verify(parkingRepository).findAll();
    }

    /**
     * Method under test: {@link AdminService#getAllParkings()}
     */
    @Test
    void testGetAllParkings5() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkings(new ArrayList<>());
        owner.setPassword("iloveyou");
        owner.setPhoneNumber("6625550144");
        owner.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner.setRoles(new HashSet<>());

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
        Parking parking = mock(Parking.class);
        when(parking.getTitle()).thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(parking.getOwner()).thenReturn(user);
        when(parking.getId()).thenReturn(1L);
        doNothing().when(parking).setAddress(Mockito.<String>any());
        doNothing().when(parking).setCity(Mockito.<String>any());
        doNothing().when(parking).setId(Mockito.<Long>any());
        doNothing().when(parking).setOwner(Mockito.<User>any());
        doNothing().when(parking).setParkingLots(Mockito.<List<ParkingLot>>any());
        doNothing().when(parking).setPricePerHour(Mockito.<BigDecimal>any());
        doNothing().when(parking).setRatings(Mockito.<List<ParkingRating>>any());
        doNothing().when(parking).setTitle(Mockito.<String>any());
        parking.setAddress("42 Main St");
        parking.setCity("Oxford");
        parking.setId(1L);
        parking.setOwner(owner);
        parking.setParkingLots(new ArrayList<>());
        parking.setPricePerHour(new BigDecimal("2.3"));
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");

        ArrayList<Parking> parkingList = new ArrayList<>();
        parkingList.add(parking);
        when(parkingRepository.findAll()).thenReturn(parkingList);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getAllParkings());
        verify(parking).getId();
        verify(parking).getOwner();
        verify(parking).getTitle();
        verify(parking).setAddress(eq("42 Main St"));
        verify(parking).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking).setTitle(eq("Dr"));
        verify(parkingRepository).findAll();
    }

    /**
     * Method under test: {@link AdminService#getParkingById(Long)}
     */
    @Test
    void testGetParkingById() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkings(new ArrayList<>());
        owner.setPassword("iloveyou");
        owner.setPhoneNumber("6625550144");
        owner.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner.setRoles(new HashSet<>());

        Parking parking = new Parking();
        parking.setAddress("42 Main St");
        parking.setCity("Oxford");
        parking.setId(1L);
        parking.setOwner(owner);
        parking.setParkingLots(new ArrayList<>());
        BigDecimal pricePerHour = new BigDecimal("2.3");
        parking.setPricePerHour(pricePerHour);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        ParkingDto actualParkingById = adminService.getParkingById(1L);

        // Assert
        verify(parkingRepository).findById(eq(1L));
        assertEquals("42 Main St", actualParkingById.getAddress());
        assertEquals("Dr", actualParkingById.getTitle());
        assertEquals("Oxford", actualParkingById.getCity());
        assertEquals(1L, actualParkingById.getId().longValue());
        assertEquals(1L, actualParkingById.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour2 = actualParkingById.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour2);
        assertSame(pricePerHour, pricePerHour2);
    }

    /**
     * Method under test: {@link AdminService#getParkingById(Long)}
     */
    @Test
    void testGetParkingById2() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkings(new ArrayList<>());
        owner.setPassword("iloveyou");
        owner.setPhoneNumber("6625550144");
        owner.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner.setRoles(new HashSet<>());

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
        Parking parking = mock(Parking.class);
        when(parking.getTitle()).thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(parking.getOwner()).thenReturn(user);
        when(parking.getId()).thenReturn(1L);
        doNothing().when(parking).setAddress(Mockito.<String>any());
        doNothing().when(parking).setCity(Mockito.<String>any());
        doNothing().when(parking).setId(Mockito.<Long>any());
        doNothing().when(parking).setOwner(Mockito.<User>any());
        doNothing().when(parking).setParkingLots(Mockito.<List<ParkingLot>>any());
        doNothing().when(parking).setPricePerHour(Mockito.<BigDecimal>any());
        doNothing().when(parking).setRatings(Mockito.<List<ParkingRating>>any());
        doNothing().when(parking).setTitle(Mockito.<String>any());
        parking.setAddress("42 Main St");
        parking.setCity("Oxford");
        parking.setId(1L);
        parking.setOwner(owner);
        parking.setParkingLots(new ArrayList<>());
        parking.setPricePerHour(new BigDecimal("2.3"));
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.getParkingById(1L));
        verify(parking).getId();
        verify(parking).getOwner();
        verify(parking).getTitle();
        verify(parking).setAddress(eq("42 Main St"));
        verify(parking).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking).setTitle(eq("Dr"));
        verify(parkingRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#deleteParking(Long)}
     */
    @Test
    void testDeleteParking() {
        // Arrange
        doNothing().when(parkingRepository).deleteById(Mockito.<Long>any());
        when(parkingRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        adminService.deleteParking(1L);

        // Assert that nothing has changed
        verify(parkingRepository).deleteById(eq(1L));
        verify(parkingRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#deleteParking(Long)}
     */
    @Test
    void testDeleteParking2() {
        // Arrange
        doThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE)).when(parkingRepository)
                .deleteById(Mockito.<Long>any());
        when(parkingRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.deleteParking(1L));
        verify(parkingRepository).deleteById(eq(1L));
        verify(parkingRepository).existsById(eq(1L));
    }

    /**
     * Method under test: {@link AdminService#deleteParking(Long)}
     */
    @Test
    void testDeleteParking3() {
        // Arrange
        when(parkingRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act and Assert
        assertThrows(JustParkException.class, () -> adminService.deleteParking(1L));
        verify(parkingRepository).existsById(eq(1L));
    }
}
