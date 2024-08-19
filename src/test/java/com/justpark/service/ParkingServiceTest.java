package com.justpark.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.justpark.exception.JustParkException;
import com.justpark.model.constant.AccountStatus;
import com.justpark.model.constant.Role;
import com.justpark.model.dto.parking.ParkingCreationDto;
import com.justpark.model.dto.parking.ParkingDto;
import com.justpark.model.dto.parking.UpdateParkingDto;
import com.justpark.model.entity.Parking;
import com.justpark.model.entity.ParkingLot;
import com.justpark.model.entity.ParkingRating;
import com.justpark.model.entity.User;
import com.justpark.repository.ParkingRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ParkingService.class})
@ExtendWith(SpringExtension.class)
class ParkingServiceTest {
    @MockBean
    private ParkingRepository parkingRepository;

    @Autowired
    private ParkingService parkingService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link ParkingService#getAuthenticatedUser(String)}
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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        User actualAuthenticatedUser = parkingService.getAuthenticatedUser("ABC123");

        // Assert
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertSame(user, actualAuthenticatedUser);
    }

    /**
     * Method under test: {@link ParkingService#getAuthenticatedUser(String)}
     */
    @Test
    void testGetAuthenticatedUser2() {
        // Arrange
        when(userService.getAuthenticatedUser(Mockito.<String>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.getAuthenticatedUser("ABC123"));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test:
     * {@link ParkingService#createParking(ParkingCreationDto, String)}
     */
    @Test
    void testCreateParking() {
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
        parking.setPricePerHour(new BigDecimal("2.3"));
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        when(parkingRepository.save(Mockito.<Parking>any())).thenReturn(parking);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        Parking actualCreateParkingResult = parkingService
                .createParking(new ParkingCreationDto("Dr", "42 Main St", "Oxford"), "ABC123");

        // Assert
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).save(isA(Parking.class));
        assertEquals("42 Main St", actualCreateParkingResult.getAddress());
        assertEquals("Dr", actualCreateParkingResult.getTitle());
        assertEquals("Oxford", actualCreateParkingResult.getCity());
        assertNull(actualCreateParkingResult.getId());
        assertNull(actualCreateParkingResult.getPricePerHour());
        assertNull(actualCreateParkingResult.getParkingLots());
        assertNull(actualCreateParkingResult.getRatings());
        assertSame(user, actualCreateParkingResult.getOwner());
    }

    /**
     * Method under test:
     * {@link ParkingService#createParking(ParkingCreationDto, String)}
     */
    @Test
    void testCreateParking2() {
        // Arrange
        when(userService.getAuthenticatedUser(Mockito.<String>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class,
                () -> parkingService.createParking(new ParkingCreationDto("Dr", "42 Main St", "Oxford"), "ABC123"));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test: {@link ParkingService#getMyParkings(String)}
     */
    @Test
    void testGetMyParkings() {
        // Arrange
        when(parkingRepository.findAllByOwner(Mockito.<User>any())).thenReturn(new ArrayList<>());

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        List<ParkingDto> actualMyParkings = parkingService.getMyParkings("ABC123");

        // Assert
        verify(parkingRepository).findAllByOwner(isA(User.class));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertTrue(actualMyParkings.isEmpty());
    }

    /**
     * Method under test: {@link ParkingService#getMyParkings(String)}
     */
    @Test
    void testGetMyParkings2() {
        // Arrange
        when(userService.getAuthenticatedUser(Mockito.<String>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.getMyParkings("ABC123"));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test: {@link ParkingService#getMyParkings(String)}
     */
    @Test
    void testGetMyParkings3() {
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
        when(parkingRepository.findAllByOwner(Mockito.<User>any())).thenReturn(parkingList);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        List<ParkingDto> actualMyParkings = parkingService.getMyParkings("ABC123");

        // Assert
        verify(parkingRepository).findAllByOwner(isA(User.class));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertEquals(1, actualMyParkings.size());
        ParkingDto getResult = actualMyParkings.get(0);
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
     * Method under test: {@link ParkingService#getMyParkings(String)}
     */
    @Test
    void testGetMyParkings4() {
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
        when(parkingRepository.findAllByOwner(Mockito.<User>any())).thenReturn(parkingList);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        List<ParkingDto> actualMyParkings = parkingService.getMyParkings("ABC123");

        // Assert
        verify(parkingRepository).findAllByOwner(isA(User.class));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertEquals(2, actualMyParkings.size());
        ParkingDto getResult = actualMyParkings.get(0);
        assertEquals("17 High St", getResult.getAddress());
        ParkingDto getResult2 = actualMyParkings.get(1);
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
     * Method under test: {@link ParkingService#getMyParkings(String)}
     */
    @Test
    void testGetMyParkings5() {
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
        when(parking.getAddress()).thenReturn("42 Main St");
        when(parking.getCity()).thenReturn("Oxford");
        when(parking.getTitle()).thenReturn("Dr");
        BigDecimal bigDecimal = new BigDecimal("2.3");
        when(parking.getPricePerHour()).thenReturn(bigDecimal);
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
        when(parkingRepository.findAllByOwner(Mockito.<User>any())).thenReturn(parkingList);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act
        List<ParkingDto> actualMyParkings = parkingService.getMyParkings("ABC123");

        // Assert
        verify(parking).getAddress();
        verify(parking).getCity();
        verify(parking).getId();
        verify(parking).getOwner();
        verify(parking).getPricePerHour();
        verify(parking).getTitle();
        verify(parking).setAddress(eq("42 Main St"));
        verify(parking).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking).setTitle(eq("Dr"));
        verify(parkingRepository).findAllByOwner(isA(User.class));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertEquals(1, actualMyParkings.size());
        ParkingDto getResult = actualMyParkings.get(0);
        assertEquals("42 Main St", getResult.getAddress());
        assertEquals("Dr", getResult.getTitle());
        assertEquals("Oxford", getResult.getCity());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour = getResult.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour);
        assertSame(bigDecimal, pricePerHour);
    }

    /**
     * Method under test: {@link ParkingService#getMyParkings(String)}
     */
    @Test
    void testGetMyParkings6() {
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
        when(parkingRepository.findAllByOwner(Mockito.<User>any())).thenReturn(parkingList);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.getMyParkings("ABC123"));
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
        verify(parkingRepository).findAllByOwner(isA(User.class));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test: {@link ParkingService#getParkingById(Long, String)}
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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        ParkingDto actualParkingById = parkingService.getParkingById(1L, "ABC123");

        // Assert
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
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
     * Method under test: {@link ParkingService#getParkingById(Long, String)}
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

        Parking parking = new Parking();
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
        when(userService.getAuthenticatedUser(Mockito.<String>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.getParkingById(1L, "ABC123"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test: {@link ParkingService#getParkingById(Long, String)}
     */
    @Test
    void testGetParkingById3() {
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
        when(parking.getId()).thenReturn(1L);
        when(parking.getAddress()).thenReturn("42 Main St");
        when(parking.getCity()).thenReturn("Oxford");
        when(parking.getTitle()).thenReturn("Dr");
        BigDecimal bigDecimal = new BigDecimal("2.3");
        when(parking.getPricePerHour()).thenReturn(bigDecimal);
        when(parking.getOwner()).thenReturn(user);
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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act
        ParkingDto actualParkingById = parkingService.getParkingById(1L, "ABC123");

        // Assert
        verify(parking).getAddress();
        verify(parking).getCity();
        verify(parking).getId();
        verify(parking, atLeast(1)).getOwner();
        verify(parking).getPricePerHour();
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
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertEquals("42 Main St", actualParkingById.getAddress());
        assertEquals("Dr", actualParkingById.getTitle());
        assertEquals("Oxford", actualParkingById.getCity());
        assertEquals(1L, actualParkingById.getId().longValue());
        assertEquals(1L, actualParkingById.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour = actualParkingById.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour);
        assertSame(bigDecimal, pricePerHour);
    }

    /**
     * Method under test: {@link ParkingService#getParkingById(Long, String)}
     */
    @Test
    void testGetParkingById4() {
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
        when(parking.getId()).thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(parking.getOwner()).thenReturn(user);
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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.getParkingById(1L, "ABC123"));
        verify(parking).getId();
        verify(parking).getOwner();
        verify(parking).setAddress(eq("42 Main St"));
        verify(parking).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking).setTitle(eq("Dr"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test: {@link ParkingService#getParkingById(Long, String)}
     */
    @Test
    void testGetParkingById5() {
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
        User user = mock(User.class);
        when(user.getId()).thenReturn(-1L);
        doNothing().when(user).setAccountStatus(Mockito.<AccountStatus>any());
        doNothing().when(user).setCountry(Mockito.<String>any());
        doNothing().when(user).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstName(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastName(Mockito.<String>any());
        doNothing().when(user).setParkings(Mockito.<List<Parking>>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhoneNumber(Mockito.<String>any());
        doNothing().when(user).setRegistrationDate(Mockito.<LocalDate>any());
        doNothing().when(user).setRoles(Mockito.<Set<Role>>any());
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
        when(parking.getOwner()).thenReturn(user);
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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.getParkingById(1L, "ABC123"));
        verify(parking).getOwner();
        verify(parking).setAddress(eq("42 Main St"));
        verify(parking).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking).setTitle(eq("Dr"));
        verify(user).getId();
        verify(user).setAccountStatus(eq(AccountStatus.ACTIVE));
        verify(user).setCountry(eq("GB"));
        verify(user).setDateOfBirth(isA(LocalDate.class));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setFirstName(eq("Jane"));
        verify(user).setId(eq(1L));
        verify(user).setLastName(eq("Doe"));
        verify(user).setParkings(isA(List.class));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhoneNumber(eq("6625550144"));
        verify(user).setRegistrationDate(isA(LocalDate.class));
        verify(user).setRoles(isA(Set.class));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test:
     * {@link ParkingService#updateParking(Long, UpdateParkingDto, String)}
     */
    @Test
    void testUpdateParking() {
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
        parking.setPricePerHour(new BigDecimal("2.3"));
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking);

        User owner2 = new User();
        owner2.setAccountStatus(AccountStatus.ACTIVE);
        owner2.setCountry("GB");
        owner2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner2.setEmail("jane.doe@example.org");
        owner2.setFirstName("Jane");
        owner2.setId(1L);
        owner2.setLastName("Doe");
        owner2.setParkings(new ArrayList<>());
        owner2.setPassword("iloveyou");
        owner2.setPhoneNumber("6625550144");
        owner2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner2.setRoles(new HashSet<>());

        Parking parking2 = new Parking();
        parking2.setAddress("42 Main St");
        parking2.setCity("Oxford");
        parking2.setId(1L);
        parking2.setOwner(owner2);
        parking2.setParkingLots(new ArrayList<>());
        parking2.setPricePerHour(new BigDecimal("2.3"));
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Dr");
        when(parkingRepository.save(Mockito.<Parking>any())).thenReturn(parking2);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);
        BigDecimal pricePerHour = new BigDecimal("2.3");

        // Act
        ParkingDto actualUpdateParkingResult = parkingService.updateParking(1L,
                new UpdateParkingDto("Dr", "42 Main St", "Oxford", pricePerHour), "ABC123");

        // Assert
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).save(isA(Parking.class));
        assertEquals("42 Main St", actualUpdateParkingResult.getAddress());
        assertEquals("Dr", actualUpdateParkingResult.getTitle());
        assertEquals("Oxford", actualUpdateParkingResult.getCity());
        assertEquals(1L, actualUpdateParkingResult.getId().longValue());
        assertEquals(1L, actualUpdateParkingResult.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour2 = actualUpdateParkingResult.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour2);
        assertSame(pricePerHour, pricePerHour2);
    }

    /**
     * Method under test:
     * {@link ParkingService#updateParking(Long, UpdateParkingDto, String)}
     */
    @Test
    void testUpdateParking2() {
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
        parking.setPricePerHour(new BigDecimal("2.3"));
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking);
        when(parkingRepository.save(Mockito.<Parking>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.updateParking(1L,
                new UpdateParkingDto("Dr", "42 Main St", "Oxford", new BigDecimal("2.3")), "ABC123"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).save(isA(Parking.class));
    }

    /**
     * Method under test:
     * {@link ParkingService#updateParking(Long, UpdateParkingDto, String)}
     */
    @Test
    void testUpdateParking3() {
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
        when(parking.getId()).thenReturn(1L);
        when(parking.getAddress()).thenReturn("42 Main St");
        when(parking.getCity()).thenReturn("Oxford");
        when(parking.getTitle()).thenReturn("Dr");
        BigDecimal bigDecimal = new BigDecimal("2.3");
        when(parking.getPricePerHour()).thenReturn(bigDecimal);
        when(parking.getOwner()).thenReturn(user);
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

        User owner2 = new User();
        owner2.setAccountStatus(AccountStatus.ACTIVE);
        owner2.setCountry("GB");
        owner2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner2.setEmail("jane.doe@example.org");
        owner2.setFirstName("Jane");
        owner2.setId(1L);
        owner2.setLastName("Doe");
        owner2.setParkings(new ArrayList<>());
        owner2.setPassword("iloveyou");
        owner2.setPhoneNumber("6625550144");
        owner2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner2.setRoles(new HashSet<>());

        Parking parking2 = new Parking();
        parking2.setAddress("42 Main St");
        parking2.setCity("Oxford");
        parking2.setId(1L);
        parking2.setOwner(owner2);
        parking2.setParkingLots(new ArrayList<>());
        parking2.setPricePerHour(new BigDecimal("2.3"));
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Dr");
        when(parkingRepository.save(Mockito.<Parking>any())).thenReturn(parking2);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act
        ParkingDto actualUpdateParkingResult = parkingService.updateParking(1L,
                new UpdateParkingDto("Dr", "42 Main St", "Oxford", new BigDecimal("2.3")), "ABC123");

        // Assert
        verify(parking).getAddress();
        verify(parking).getCity();
        verify(parking).getId();
        verify(parking, atLeast(1)).getOwner();
        verify(parking).getPricePerHour();
        verify(parking).getTitle();
        verify(parking, atLeast(1)).setAddress(eq("42 Main St"));
        verify(parking, atLeast(1)).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking, atLeast(1)).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking, atLeast(1)).setTitle(eq("Dr"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).save(isA(Parking.class));
        assertEquals("42 Main St", actualUpdateParkingResult.getAddress());
        assertEquals("Dr", actualUpdateParkingResult.getTitle());
        assertEquals("Oxford", actualUpdateParkingResult.getCity());
        assertEquals(1L, actualUpdateParkingResult.getId().longValue());
        assertEquals(1L, actualUpdateParkingResult.getOwnerId().longValue());
        BigDecimal expectedPricePerHour = new BigDecimal("2.3");
        BigDecimal pricePerHour = actualUpdateParkingResult.getPricePerHour();
        assertEquals(expectedPricePerHour, pricePerHour);
        assertSame(bigDecimal, pricePerHour);
    }

    /**
     * Method under test:
     * {@link ParkingService#updateParking(Long, UpdateParkingDto, String)}
     */
    @Test
    void testUpdateParking4() {
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
        when(parking.getId()).thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(parking.getOwner()).thenReturn(user);
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

        User owner2 = new User();
        owner2.setAccountStatus(AccountStatus.ACTIVE);
        owner2.setCountry("GB");
        owner2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner2.setEmail("jane.doe@example.org");
        owner2.setFirstName("Jane");
        owner2.setId(1L);
        owner2.setLastName("Doe");
        owner2.setParkings(new ArrayList<>());
        owner2.setPassword("iloveyou");
        owner2.setPhoneNumber("6625550144");
        owner2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner2.setRoles(new HashSet<>());

        Parking parking2 = new Parking();
        parking2.setAddress("42 Main St");
        parking2.setCity("Oxford");
        parking2.setId(1L);
        parking2.setOwner(owner2);
        parking2.setParkingLots(new ArrayList<>());
        parking2.setPricePerHour(new BigDecimal("2.3"));
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Dr");
        when(parkingRepository.save(Mockito.<Parking>any())).thenReturn(parking2);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.updateParking(1L,
                new UpdateParkingDto("Dr", "42 Main St", "Oxford", new BigDecimal("2.3")), "ABC123"));
        verify(parking).getId();
        verify(parking).getOwner();
        verify(parking, atLeast(1)).setAddress(eq("42 Main St"));
        verify(parking, atLeast(1)).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking, atLeast(1)).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking, atLeast(1)).setTitle(eq("Dr"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).save(isA(Parking.class));
    }

    /**
     * Method under test: {@link ParkingService#deleteParking(Long, String)}
     */
    @Test
    void testDeleteParking() {
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
        parking.setPricePerHour(new BigDecimal("2.3"));
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking);
        doNothing().when(parkingRepository).delete(Mockito.<Parking>any());
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        boolean actualDeleteParkingResult = parkingService.deleteParking(1L, "ABC123");

        // Assert
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).delete(isA(Parking.class));
        assertTrue(actualDeleteParkingResult);
    }

    /**
     * Method under test: {@link ParkingService#deleteParking(Long, String)}
     */
    @Test
    void testDeleteParking2() {
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
        parking.setPricePerHour(new BigDecimal("2.3"));
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking);
        doThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE)).when(parkingRepository)
                .delete(Mockito.<Parking>any());
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.deleteParking(1L, "ABC123"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).delete(isA(Parking.class));
    }

    /**
     * Method under test: {@link ParkingService#deleteParking(Long, String)}
     */
    @Test
    void testDeleteParking3() {
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
        when(parking.getOwner()).thenReturn(user);
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
        doNothing().when(parkingRepository).delete(Mockito.<Parking>any());
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act
        boolean actualDeleteParkingResult = parkingService.deleteParking(1L, "ABC123");

        // Assert
        verify(parking).getOwner();
        verify(parking).setAddress(eq("42 Main St"));
        verify(parking).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking).setTitle(eq("Dr"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingRepository).delete(isA(Parking.class));
        assertTrue(actualDeleteParkingResult);
    }

    /**
     * Method under test: {@link ParkingService#deleteParking(Long, String)}
     */
    @Test
    void testDeleteParking4() {
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
        User user = mock(User.class);
        when(user.getId()).thenReturn(-1L);
        doNothing().when(user).setAccountStatus(Mockito.<AccountStatus>any());
        doNothing().when(user).setCountry(Mockito.<String>any());
        doNothing().when(user).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setFirstName(Mockito.<String>any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setLastName(Mockito.<String>any());
        doNothing().when(user).setParkings(Mockito.<List<Parking>>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setPhoneNumber(Mockito.<String>any());
        doNothing().when(user).setRegistrationDate(Mockito.<LocalDate>any());
        doNothing().when(user).setRoles(Mockito.<Set<Role>>any());
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
        when(parking.getOwner()).thenReturn(user);
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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user2);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingService.deleteParking(1L, "ABC123"));
        verify(parking).getOwner();
        verify(parking).setAddress(eq("42 Main St"));
        verify(parking).setCity(eq("Oxford"));
        verify(parking).setId(eq(1L));
        verify(parking).setOwner(isA(User.class));
        verify(parking).setParkingLots(isA(List.class));
        verify(parking).setPricePerHour(isA(BigDecimal.class));
        verify(parking).setRatings(isA(List.class));
        verify(parking).setTitle(eq("Dr"));
        verify(user).getId();
        verify(user).setAccountStatus(eq(AccountStatus.ACTIVE));
        verify(user).setCountry(eq("GB"));
        verify(user).setDateOfBirth(isA(LocalDate.class));
        verify(user).setEmail(eq("jane.doe@example.org"));
        verify(user).setFirstName(eq("Jane"));
        verify(user).setId(eq(1L));
        verify(user).setLastName(eq("Doe"));
        verify(user).setParkings(isA(List.class));
        verify(user).setPassword(eq("iloveyou"));
        verify(user).setPhoneNumber(eq("6625550144"));
        verify(user).setRegistrationDate(isA(LocalDate.class));
        verify(user).setRoles(isA(Set.class));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test: {@link ParkingService#deleteParking(Long, String)}
     */
    @Test
    void testDeleteParking5() {
        // Arrange
        Optional<Parking> emptyResult = Optional.empty();
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

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
        when(userService.getAuthenticatedUser(Mockito.<String>any())).thenReturn(user);

        // Act
        boolean actualDeleteParkingResult = parkingService.deleteParking(1L, "ABC123");

        // Assert
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertFalse(actualDeleteParkingResult);
    }
}
