package com.parking.JustPark.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    private UserRepository userRepository;

    /**
     * Method under test: {@link ParkingService#createParking(Parking, Long)}
     */
    @Test
    void testCreateParking() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(mock(Date.class));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkingList(new ArrayList<>());
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
        parking.setPriceDefault(10.0d);
        parking.setPriceVip(10.0d);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        when(parkingRepository.save(Mockito.<Parking>any())).thenReturn(parking);

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

        User owner2 = new User();
        owner2.setAccountStatus(AccountStatus.ACTIVE);
        owner2.setCountry("GB");
        owner2.setDateOfBirth(mock(Date.class));
        owner2.setEmail("jane.doe@example.org");
        owner2.setFirstName("Jane");
        owner2.setId(1L);
        owner2.setLastName("Doe");
        owner2.setParkingList(new ArrayList<>());
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
        parking2.setPriceDefault(10.0d);
        parking2.setPriceVip(10.0d);
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Dr");

        // Act
        boolean actualCreateParkingResult = parkingService.createParking(parking2, 1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(parkingRepository).save(Mockito.<Parking>any());
        assertTrue(actualCreateParkingResult);
        assertSame(user, parking2.getOwner());
    }

    /**
     * Method under test: {@link ParkingService#createParking(Parking, Long)}
     */
    @Test
    void testCreateParking2() {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(mock(Date.class));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkingList(new ArrayList<>());
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
        parking.setPriceDefault(10.0d);
        parking.setPriceVip(10.0d);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");

        // Act
        boolean actualCreateParkingResult = parkingService.createParking(parking, 1L);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        assertFalse(actualCreateParkingResult);
    }

    /**
     * Method under test: {@link ParkingService#deleteParking(Long)}
     */
    @Test
    void testDeleteParking() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(mock(Date.class));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkingList(new ArrayList<>());
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
        parking.setPriceDefault(10.0d);
        parking.setPriceVip(10.0d);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        Optional<Parking> ofResult = Optional.of(parking);
        doNothing().when(parkingRepository).delete(Mockito.<Parking>any());
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualDeleteParkingResult = parkingService.deleteParking(1L);

        // Assert
        verify(parkingRepository).delete(Mockito.<Parking>any());
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertFalse(actualDeleteParkingResult);
    }

    /**
     * Method under test: {@link ParkingService#deleteParking(Long)}
     */
    @Test
    void testDeleteParking2() {
        // Arrange
        Optional<Parking> emptyResult = Optional.empty();
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        boolean actualDeleteParkingResult = parkingService.deleteParking(1L);

        // Assert
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertFalse(actualDeleteParkingResult);
    }

    /**
     * Method under test: {@link ParkingService#editParking(Parking)}
     */
    @Test
    void testEditParking() {
        // Arrange
        User owner = new User();
        owner.setAccountStatus(AccountStatus.ACTIVE);
        owner.setCountry("GB");
        owner.setDateOfBirth(mock(Date.class));
        owner.setEmail("jane.doe@example.org");
        owner.setFirstName("Jane");
        owner.setId(1L);
        owner.setLastName("Doe");
        owner.setParkingList(new ArrayList<>());
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
        parking.setPriceDefault(10.0d);
        parking.setPriceVip(10.0d);
        parking.setRatings(new ArrayList<>());
        parking.setTitle("Dr");
        when(parkingRepository.save(Mockito.<Parking>any())).thenReturn(parking);

        User owner2 = new User();
        owner2.setAccountStatus(AccountStatus.ACTIVE);
        owner2.setCountry("GB");
        owner2.setDateOfBirth(mock(Date.class));
        owner2.setEmail("jane.doe@example.org");
        owner2.setFirstName("Jane");
        owner2.setId(1L);
        owner2.setLastName("Doe");
        owner2.setParkingList(new ArrayList<>());
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
        parking2.setPriceDefault(10.0d);
        parking2.setPriceVip(10.0d);
        parking2.setRatings(new ArrayList<>());
        parking2.setTitle("Dr");

        // Act
        boolean actualEditParkingResult = parkingService.editParking(parking2);

        // Assert
        verify(parkingRepository).save(Mockito.<Parking>any());
        assertTrue(actualEditParkingResult);
    }
}
