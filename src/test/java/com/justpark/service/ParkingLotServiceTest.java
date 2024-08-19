package com.justpark.service;

import com.justpark.exception.JustParkException;
import com.justpark.model.constant.AccountStatus;
import com.justpark.model.constant.Role;
import com.justpark.model.dto.parkingLot.ParkingLotResponseDto;
import com.justpark.model.entity.Parking;
import com.justpark.model.entity.ParkingLot;
import com.justpark.model.entity.ParkingRating;
import com.justpark.model.entity.User;
import com.justpark.repository.ParkingLotRepository;
import com.justpark.repository.ParkingRepository;
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

@ContextConfiguration(classes = {ParkingLotService.class})
@ExtendWith(SpringExtension.class)
class ParkingLotServiceTest {
    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingRepository parkingRepository;

    @MockBean
    private UserService userService;

    /**
     * Method under test:
     * {@link ParkingLotService#createParkingLot(Long, List, String)}
     */
    @Test
    void testCreateParkingLot() {
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
        List<ParkingLotResponseDto> actualCreateParkingLotResult = parkingLotService.createParkingLot(1L, new ArrayList<>(),
                "ABC123");

        // Assert
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertTrue(actualCreateParkingLotResult.isEmpty());
    }

    /**
     * Method under test:
     * {@link ParkingLotService#createParkingLot(Long, List, String)}
     */
    @Test
    void testCreateParkingLot2() {
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
        assertThrows(JustParkException.class, () -> parkingLotService.createParkingLot(1L, new ArrayList<>(), "ABC123"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test:
     * {@link ParkingLotService#createParkingLot(Long, List, String)}
     */
    @Test
    void testCreateParkingLot3() {
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
        assertThrows(JustParkException.class, () -> parkingLotService.createParkingLot(1L, new ArrayList<>(), "ABC123"));
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
     * {@link ParkingLotService#createParkingLot(Long, List, String)}
     */
    @Test
    void testCreateParkingLot4() {
        // Arrange
        Optional<Parking> emptyResult = Optional.empty();
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingLotService.createParkingLot(1L, new ArrayList<>(), "ABC123"));
        verify(parkingRepository).findById(eq(1L));
    }

    /**
     * Method under test: {@link ParkingLotService#listByParking(Long, String)}
     */
    @Test
    void testListByParking() {
        // Arrange
        when(parkingLotRepository.findAllByParking(Mockito.<Parking>any())).thenReturn(new ArrayList<>());

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
        List<ParkingLotResponseDto> actualListByParkingResult = parkingLotService.listByParking(1L, "ABC123");

        // Assert
        verify(parkingLotRepository).findAllByParking(isA(Parking.class));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertNull(actualListByParkingResult);
    }

    /**
     * Method under test: {@link ParkingLotService#listByParking(Long, String)}
     */
    @Test
    void testListByParking2() {
        // Arrange
        when(parkingLotRepository.findAllByParking(Mockito.<Parking>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

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
        assertThrows(JustParkException.class, () -> parkingLotService.listByParking(1L, "ABC123"));
        verify(parkingLotRepository).findAllByParking(isA(Parking.class));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
    }

    /**
     * Method under test: {@link ParkingLotService#listByParking(Long, String)}
     */
    @Test
    void testListByParking3() {
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

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTitle("Dr");

        ArrayList<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingLot);
        when(parkingLotRepository.findAllByParking(Mockito.<Parking>any())).thenReturn(parkingLotList);

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
        Optional<Parking> ofResult = Optional.of(parking2);
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
        List<ParkingLotResponseDto> actualListByParkingResult = parkingLotService.listByParking(1L, "ABC123");

        // Assert
        verify(parkingLotRepository).findAllByParking(isA(Parking.class));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertEquals(1, actualListByParkingResult.size());
        ParkingLotResponseDto getResult = actualListByParkingResult.get(0);
        assertEquals("Dr", getResult.getTitle());
        assertEquals(1, getResult.getLayer().intValue());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getParkingId().longValue());
        assertTrue(getResult.getIsEmpty());
    }

    /**
     * Method under test: {@link ParkingLotService#listByParking(Long, String)}
     */
    @Test
    void testListByParking4() {
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

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTitle("Dr");

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
        parking2.setPricePerHour(new BigDecimal("2.3"));
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Mr");

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setId(2L);
        parkingLot2.setIsEmpty(false);
        parkingLot2.setLayer(0);
        parkingLot2.setParking(parking2);
        parkingLot2.setTitle("Mr");

        ArrayList<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingLot2);
        parkingLotList.add(parkingLot);
        when(parkingLotRepository.findAllByParking(Mockito.<Parking>any())).thenReturn(parkingLotList);

        User owner3 = new User();
        owner3.setAccountStatus(AccountStatus.ACTIVE);
        owner3.setCountry("GB");
        owner3.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner3.setEmail("jane.doe@example.org");
        owner3.setFirstName("Jane");
        owner3.setId(1L);
        owner3.setLastName("Doe");
        owner3.setParkings(new ArrayList<>());
        owner3.setPassword("iloveyou");
        owner3.setPhoneNumber("6625550144");
        owner3.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner3.setRoles(new HashSet<>());

        Parking parking3 = new Parking();
        parking3.setAddress("42 Main St");
        parking3.setCity("Oxford");
        parking3.setId(1L);
        parking3.setOwner(owner3);
        parking3.setParkingLots(new ArrayList<>());
        parking3.setPricePerHour(new BigDecimal("2.3"));
        parking3.setRatings(new ArrayList<>());
        parking3.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking3);
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
        List<ParkingLotResponseDto> actualListByParkingResult = parkingLotService.listByParking(1L, "ABC123");

        // Assert
        verify(parkingLotRepository).findAllByParking(isA(Parking.class));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        assertEquals(2, actualListByParkingResult.size());
        ParkingLotResponseDto getResult = actualListByParkingResult.get(1);
        assertEquals("Dr", getResult.getTitle());
        ParkingLotResponseDto getResult2 = actualListByParkingResult.get(0);
        assertEquals("Mr", getResult2.getTitle());
        assertEquals(0, getResult2.getLayer().intValue());
        assertEquals(1, getResult.getLayer().intValue());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals(1L, getResult.getParkingId().longValue());
        assertEquals(2L, getResult2.getId().longValue());
        assertEquals(2L, getResult2.getParkingId().longValue());
        assertFalse(getResult2.getIsEmpty());
        assertTrue(getResult.getIsEmpty());
    }

    /**
     * Method under test: {@link ParkingLotService#listByParking(Long, String)}
     */
    @Test
    void testListByParking5() {
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
        assertThrows(JustParkException.class, () -> parkingLotService.listByParking(1L, "ABC123"));
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
     * Method under test: {@link ParkingLotService#listByParking(Long, String)}
     */
    @Test
    void testListByParking6() {
        // Arrange
        Optional<Parking> emptyResult = Optional.empty();
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(JustParkException.class, () -> parkingLotService.listByParking(1L, "ABC123"));
        verify(parkingRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link ParkingLotService#getParkingLotById(Long, Long, String)}
     */
    @Test
    void testGetParkingLotById() {
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

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTitle("Dr");
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        Optional<Parking> ofResult2 = Optional.of(parking2);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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
        ParkingLotResponseDto actualParkingLotById = parkingLotService.getParkingLotById(1L, 1L, "ABC123");

        // Assert
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingLotRepository).findById(eq(1L));
        assertEquals("Dr", actualParkingLotById.getTitle());
        assertEquals(1, actualParkingLotById.getLayer().intValue());
        assertEquals(1L, actualParkingLotById.getId().longValue());
        assertEquals(1L, actualParkingLotById.getParkingId().longValue());
        assertTrue(actualParkingLotById.getIsEmpty());
    }

    /**
     * Method under test:
     * {@link ParkingLotService#getParkingLotById(Long, Long, String)}
     */
    @Test
    void testGetParkingLotById2() {
        // Arrange
        when(parkingLotRepository.findById(Mockito.<Long>any()))
                .thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));

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
        assertThrows(JustParkException.class, () -> parkingLotService.getParkingLotById(1L, 1L, "ABC123"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingLotRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link ParkingLotService#getParkingLotById(Long, Long, String)}
     */
    @Test
    void testGetParkingLotById3() {
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
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getIsEmpty()).thenReturn(true);
        when(parkingLot.getLayer()).thenReturn(1);
        when(parkingLot.getTitle()).thenReturn("Dr");
        when(parkingLot.getParking()).thenReturn(parking2);
        when(parkingLot.getId()).thenReturn(1L);
        doNothing().when(parkingLot).setId(Mockito.<Long>any());
        doNothing().when(parkingLot).setIsEmpty(Mockito.<Boolean>any());
        doNothing().when(parkingLot).setLayer(Mockito.<Integer>any());
        doNothing().when(parkingLot).setParking(Mockito.<Parking>any());
        doNothing().when(parkingLot).setTitle(Mockito.<String>any());
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTitle("Dr");
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User owner3 = new User();
        owner3.setAccountStatus(AccountStatus.ACTIVE);
        owner3.setCountry("GB");
        owner3.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner3.setEmail("jane.doe@example.org");
        owner3.setFirstName("Jane");
        owner3.setId(1L);
        owner3.setLastName("Doe");
        owner3.setParkings(new ArrayList<>());
        owner3.setPassword("iloveyou");
        owner3.setPhoneNumber("6625550144");
        owner3.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner3.setRoles(new HashSet<>());

        Parking parking3 = new Parking();
        parking3.setAddress("42 Main St");
        parking3.setCity("Oxford");
        parking3.setId(1L);
        parking3.setOwner(owner3);
        parking3.setParkingLots(new ArrayList<>());
        parking3.setPricePerHour(new BigDecimal("2.3"));
        parking3.setRatings(new ArrayList<>());
        parking3.setTitle("Dr");
        Optional<Parking> ofResult2 = Optional.of(parking3);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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
        ParkingLotResponseDto actualParkingLotById = parkingLotService.getParkingLotById(1L, 1L, "ABC123");

        // Assert
        verify(parkingLot).getId();
        verify(parkingLot).getIsEmpty();
        verify(parkingLot).getLayer();
        verify(parkingLot).getParking();
        verify(parkingLot).getTitle();
        verify(parkingLot).setId(eq(1L));
        verify(parkingLot).setIsEmpty(eq(true));
        verify(parkingLot).setLayer(eq(1));
        verify(parkingLot).setParking(isA(Parking.class));
        verify(parkingLot).setTitle(eq("Dr"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingLotRepository).findById(eq(1L));
        assertEquals("Dr", actualParkingLotById.getTitle());
        assertEquals(1, actualParkingLotById.getLayer().intValue());
        assertEquals(1L, actualParkingLotById.getId().longValue());
        assertEquals(1L, actualParkingLotById.getParkingId().longValue());
        assertTrue(actualParkingLotById.getIsEmpty());
    }

    /**
     * Method under test:
     * {@link ParkingLotService#getParkingLotById(Long, Long, String)}
     */
    @Test
    void testGetParkingLotById4() {
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
        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getTitle()).thenThrow(new JustParkException("An error occurred", HttpStatus.CONTINUE));
        when(parkingLot.getParking()).thenReturn(parking2);
        when(parkingLot.getId()).thenReturn(1L);
        doNothing().when(parkingLot).setId(Mockito.<Long>any());
        doNothing().when(parkingLot).setIsEmpty(Mockito.<Boolean>any());
        doNothing().when(parkingLot).setLayer(Mockito.<Integer>any());
        doNothing().when(parkingLot).setParking(Mockito.<Parking>any());
        doNothing().when(parkingLot).setTitle(Mockito.<String>any());
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTitle("Dr");
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User owner3 = new User();
        owner3.setAccountStatus(AccountStatus.ACTIVE);
        owner3.setCountry("GB");
        owner3.setDateOfBirth(LocalDate.of(1970, 1, 1));
        owner3.setEmail("jane.doe@example.org");
        owner3.setFirstName("Jane");
        owner3.setId(1L);
        owner3.setLastName("Doe");
        owner3.setParkings(new ArrayList<>());
        owner3.setPassword("iloveyou");
        owner3.setPhoneNumber("6625550144");
        owner3.setRegistrationDate(LocalDate.of(1970, 1, 1));
        owner3.setRoles(new HashSet<>());

        Parking parking3 = new Parking();
        parking3.setAddress("42 Main St");
        parking3.setCity("Oxford");
        parking3.setId(1L);
        parking3.setOwner(owner3);
        parking3.setParkingLots(new ArrayList<>());
        parking3.setPricePerHour(new BigDecimal("2.3"));
        parking3.setRatings(new ArrayList<>());
        parking3.setTitle("Dr");
        Optional<Parking> ofResult2 = Optional.of(parking3);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

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
        assertThrows(JustParkException.class, () -> parkingLotService.getParkingLotById(1L, 1L, "ABC123"));
        verify(parkingLot).getId();
        verify(parkingLot).getParking();
        verify(parkingLot).getTitle();
        verify(parkingLot).setId(eq(1L));
        verify(parkingLot).setIsEmpty(eq(true));
        verify(parkingLot).setLayer(eq(1));
        verify(parkingLot).setParking(isA(Parking.class));
        verify(parkingLot).setTitle(eq("Dr"));
        verify(parkingRepository).findById(eq(1L));
        verify(userService).getAuthenticatedUser(eq("ABC123"));
        verify(parkingLotRepository).findById(eq(1L));
    }
}
