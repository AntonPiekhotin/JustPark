package com.parking.JustPark.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.parking.JustPark.entity.Customer;
import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.ParkingRating;
import com.parking.JustPark.entity.User;
import com.parking.JustPark.entity.enums.AccountStatus;
import com.parking.JustPark.repository.ParkingRatingRepository;
import com.parking.JustPark.repository.ParkingRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ParkingRatingService.class})
@ExtendWith(SpringExtension.class)
class ParkingRatingServiceDiffblueTest {
    @MockBean
    private ParkingRatingRepository parkingRatingRepository;

    @Autowired
    private ParkingRatingService parkingRatingService;

    @MockBean
    private ParkingRepository parkingRepository;

    /**
     * Method under test: {@link ParkingRatingService#listOfRatingsByParking(Long)}
     */
    @Test
    void testListOfRatingsByParking() {
        // Arrange
        ArrayList<ParkingRating> parkingRatingList = new ArrayList<>();
        when(parkingRatingRepository.findAllByParking(Mockito.<Parking>any())).thenReturn(parkingRatingList);

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
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        List<ParkingRating> actualListOfRatingsByParkingResult = parkingRatingService.listOfRatingsByParking(1L);

        // Assert
        verify(parkingRatingRepository).findAllByParking(Mockito.<Parking>any());
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertTrue(actualListOfRatingsByParkingResult.isEmpty());
        assertSame(parkingRatingList, actualListOfRatingsByParkingResult);
    }

    /**
     * Method under test: {@link ParkingRatingService#listOfRatingsByParking(Long)}
     */
    @Test
    void testListOfRatingsByParking2() {
        // Arrange
        Optional<Parking> emptyResult = Optional.empty();
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        List<ParkingRating> actualListOfRatingsByParkingResult = parkingRatingService.listOfRatingsByParking(1L);

        // Assert
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertNull(actualListOfRatingsByParkingResult);
    }

    /**
     * Method under test: {@link ParkingRatingService#getRatingByParking(Long)}
     */
    @Test
    void testGetRatingByParking() {
        // Arrange
        when(parkingRatingRepository.findAllByParking(Mockito.<Parking>any())).thenReturn(new ArrayList<>());
        when(parkingRatingRepository.existsByParking(Mockito.<Parking>any())).thenReturn(true);

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
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        int actualRatingByParking = parkingRatingService.getRatingByParking(1L);

        // Assert
        verify(parkingRatingRepository).existsByParking(Mockito.<Parking>any());
        verify(parkingRatingRepository).findAllByParking(Mockito.<Parking>any());
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertEquals(-1, actualRatingByParking);
    }

    /**
     * Method under test: {@link ParkingRatingService#getRatingByParking(Long)}
     */
    @Test
    void testGetRatingByParking2() {
        // Arrange
        Customer customer = new Customer();
        customer.setAccountStatus("3");
        customer.setCity("Oxford");
        customer.setCountry("GB");
        customer.setDateOfBirth(mock(Date.class));
        customer.setEmail("jane.doe@example.org");
        customer.setId(1L);
        customer.setName("Name");
        customer.setPassword("iloveyou");
        customer.setPhoneNumber("6625550144");
        customer.setRegistrationDate(LocalDate.of(1970, 1, 1));

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

        ParkingRating parkingRating = new ParkingRating();
        parkingRating.setCustomer(customer);
        parkingRating.setId(1L);
        parkingRating.setParking(parking);
        parkingRating.setRating(1);

        ArrayList<ParkingRating> parkingRatingList = new ArrayList<>();
        parkingRatingList.add(parkingRating);
        when(parkingRatingRepository.findAllByParking(Mockito.<Parking>any())).thenReturn(parkingRatingList);
        when(parkingRatingRepository.existsByParking(Mockito.<Parking>any())).thenReturn(true);

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
        Optional<Parking> ofResult = Optional.of(parking2);
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        int actualRatingByParking = parkingRatingService.getRatingByParking(1L);

        // Assert
        verify(parkingRatingRepository).existsByParking(Mockito.<Parking>any());
        verify(parkingRatingRepository).findAllByParking(Mockito.<Parking>any());
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertEquals(0, actualRatingByParking);
    }

    /**
     * Method under test: {@link ParkingRatingService#getRatingByParking(Long)}
     */
    @Test
    void testGetRatingByParking3() {
        // Arrange
        when(parkingRatingRepository.existsByParking(Mockito.<Parking>any())).thenReturn(false);

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
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        int actualRatingByParking = parkingRatingService.getRatingByParking(1L);

        // Assert
        verify(parkingRatingRepository).existsByParking(Mockito.<Parking>any());
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertEquals(0, actualRatingByParking);
    }

    /**
     * Method under test: {@link ParkingRatingService#getRatingByParking(Long)}
     */
    @Test
    void testGetRatingByParking4() {
        // Arrange
        Optional<Parking> emptyResult = Optional.empty();
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        int actualRatingByParking = parkingRatingService.getRatingByParking(1L);

        // Assert
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertEquals(-1, actualRatingByParking);
    }
}
