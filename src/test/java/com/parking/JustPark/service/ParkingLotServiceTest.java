package com.parking.JustPark.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.parking.JustPark.model.entity.Customer;
import com.parking.JustPark.model.entity.Parking;
import com.parking.JustPark.model.entity.ParkingLot;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.model.constant.AccountStatus;
import com.parking.JustPark.repository.CustomerRepository;
import com.parking.JustPark.repository.ParkingLotRepository;
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

@ContextConfiguration(classes = {ParkingLotService.class})
@ExtendWith(SpringExtension.class)
class ParkingLotServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingRepository parkingRepository;

    /**
     * Method under test:
     * {@link ParkingLotService#createParkingLot(ParkingLot, Long)}
     */
    @Test
    void testCreateParkingLot() {
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

        Customer takenBy = new Customer();
        takenBy.setAccountStatus("3");
        takenBy.setCity("Oxford");
        takenBy.setCountry("GB");
        takenBy.setDateOfBirth(mock(Date.class));
        takenBy.setEmail("jane.doe@example.org");
        takenBy.setId(1L);
        takenBy.setName("Name");
        takenBy.setPassword("iloveyou");
        takenBy.setPhoneNumber("6625550144");
        takenBy.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setIsVip(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTakenBy(takenBy);
        when(parkingLotRepository.save(Mockito.<ParkingLot>any())).thenReturn(parkingLot);

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

        User owner3 = new User();
        owner3.setAccountStatus(AccountStatus.ACTIVE);
        owner3.setCountry("GB");
        owner3.setDateOfBirth(mock(Date.class));
        owner3.setEmail("jane.doe@example.org");
        owner3.setFirstName("Jane");
        owner3.setId(1L);
        owner3.setLastName("Doe");
        owner3.setParkingList(new ArrayList<>());
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
        parking3.setPriceDefault(10.0d);
        parking3.setPriceVip(10.0d);
        parking3.setRatings(new ArrayList<>());
        parking3.setTitle("Dr");

        Customer takenBy2 = new Customer();
        takenBy2.setAccountStatus("3");
        takenBy2.setCity("Oxford");
        takenBy2.setCountry("GB");
        takenBy2.setDateOfBirth(mock(Date.class));
        takenBy2.setEmail("jane.doe@example.org");
        takenBy2.setId(1L);
        takenBy2.setName("Name");
        takenBy2.setPassword("iloveyou");
        takenBy2.setPhoneNumber("6625550144");
        takenBy2.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setId(1L);
        parkingLot2.setIsEmpty(true);
        parkingLot2.setIsVip(true);
        parkingLot2.setLayer(1);
        parkingLot2.setParking(parking3);
        parkingLot2.setTakenBy(takenBy2);

        // Act
        boolean actualCreateParkingLotResult = parkingLotService.createParkingLot(parkingLot2, 1L);

        // Assert
        verify(parkingRepository).findById(Mockito.<Long>any());
        verify(parkingLotRepository).save(Mockito.<ParkingLot>any());
        assertFalse(parkingLot2.getIsEmpty());
        assertTrue(actualCreateParkingLotResult);
        assertSame(parking2, parkingLot2.getParking());
    }

    /**
     * Method under test: {@link ParkingLotService#listByParking(Long)}
     */
    @Test
    void testListByParking() {
        // Arrange
        ArrayList<ParkingLot> parkingLotList = new ArrayList<>();
        when(parkingLotRepository.findAllByParking(Mockito.<Parking>any())).thenReturn(parkingLotList);

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
        List<ParkingLot> actualListByParkingResult = parkingLotService.listByParking(1L);

        // Assert
        verify(parkingLotRepository).findAllByParking(Mockito.<Parking>any());
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertTrue(actualListByParkingResult.isEmpty());
        assertSame(parkingLotList, actualListByParkingResult);
    }

    /**
     * Method under test: {@link ParkingLotService#listByParking(Long)}
     */
    @Test
    void testListByParking2() {
        // Arrange
        Optional<Parking> emptyResult = Optional.empty();
        when(parkingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act
        List<ParkingLot> actualListByParkingResult = parkingLotService.listByParking(1L);

        // Assert
        verify(parkingRepository).findById(Mockito.<Long>any());
        assertNull(actualListByParkingResult);
    }

    /**
     * Method under test: {@link ParkingLotService#changeLotStatus(Long)}
     */
    @Test
    void testChangeLotStatus() {
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

        Customer takenBy = new Customer();
        takenBy.setAccountStatus("3");
        takenBy.setCity("Oxford");
        takenBy.setCountry("GB");
        takenBy.setDateOfBirth(mock(Date.class));
        takenBy.setEmail("jane.doe@example.org");
        takenBy.setId(1L);
        takenBy.setName("Name");
        takenBy.setPassword("iloveyou");
        takenBy.setPhoneNumber("6625550144");
        takenBy.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setIsVip(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTakenBy(takenBy);
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);

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

        Customer takenBy2 = new Customer();
        takenBy2.setAccountStatus("3");
        takenBy2.setCity("Oxford");
        takenBy2.setCountry("GB");
        takenBy2.setDateOfBirth(mock(Date.class));
        takenBy2.setEmail("jane.doe@example.org");
        takenBy2.setId(1L);
        takenBy2.setName("Name");
        takenBy2.setPassword("iloveyou");
        takenBy2.setPhoneNumber("6625550144");
        takenBy2.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setId(1L);
        parkingLot2.setIsEmpty(true);
        parkingLot2.setIsVip(true);
        parkingLot2.setLayer(1);
        parkingLot2.setParking(parking2);
        parkingLot2.setTakenBy(takenBy2);
        when(parkingLotRepository.save(Mockito.<ParkingLot>any())).thenReturn(parkingLot2);
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualChangeLotStatusResult = parkingLotService.changeLotStatus(1L);

        // Assert
        verify(parkingLotRepository).findById(Mockito.<Long>any());
        verify(parkingLotRepository).save(Mockito.<ParkingLot>any());
        assertTrue(actualChangeLotStatusResult);
    }

    /**
     * Method under test: {@link ParkingLotService#reserveParkingLot(Long, Long)}
     */
    @Test
    void testReserveParkingLot() {
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

        Customer takenBy = new Customer();
        takenBy.setAccountStatus("3");
        takenBy.setCity("Oxford");
        takenBy.setCountry("GB");
        takenBy.setDateOfBirth(mock(Date.class));
        takenBy.setEmail("jane.doe@example.org");
        takenBy.setId(1L);
        takenBy.setName("Name");
        takenBy.setPassword("iloveyou");
        takenBy.setPhoneNumber("6625550144");
        takenBy.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setIsVip(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTakenBy(takenBy);
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);

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

        Customer takenBy2 = new Customer();
        takenBy2.setAccountStatus("3");
        takenBy2.setCity("Oxford");
        takenBy2.setCountry("GB");
        takenBy2.setDateOfBirth(mock(Date.class));
        takenBy2.setEmail("jane.doe@example.org");
        takenBy2.setId(1L);
        takenBy2.setName("Name");
        takenBy2.setPassword("iloveyou");
        takenBy2.setPhoneNumber("6625550144");
        takenBy2.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setId(1L);
        parkingLot2.setIsEmpty(true);
        parkingLot2.setIsVip(true);
        parkingLot2.setLayer(1);
        parkingLot2.setParking(parking2);
        parkingLot2.setTakenBy(takenBy2);
        when(parkingLotRepository.save(Mockito.<ParkingLot>any())).thenReturn(parkingLot2);
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        // Act
        boolean actualReserveParkingLotResult = parkingLotService.reserveParkingLot(1L, 1L);

        // Assert
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(parkingLotRepository).findById(Mockito.<Long>any());
        verify(parkingLotRepository).save(Mockito.<ParkingLot>any());
        assertTrue(actualReserveParkingLotResult);
    }

    /**
     * Method under test: {@link ParkingLotService#releaseParkingLot(Long)}
     */
    @Test
    void testReleaseParkingLot() {
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

        Customer takenBy = new Customer();
        takenBy.setAccountStatus("3");
        takenBy.setCity("Oxford");
        takenBy.setCountry("GB");
        takenBy.setDateOfBirth(mock(Date.class));
        takenBy.setEmail("jane.doe@example.org");
        takenBy.setId(1L);
        takenBy.setName("Name");
        takenBy.setPassword("iloveyou");
        takenBy.setPhoneNumber("6625550144");
        takenBy.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setIsVip(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTakenBy(takenBy);
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);

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

        Customer takenBy2 = new Customer();
        takenBy2.setAccountStatus("3");
        takenBy2.setCity("Oxford");
        takenBy2.setCountry("GB");
        takenBy2.setDateOfBirth(mock(Date.class));
        takenBy2.setEmail("jane.doe@example.org");
        takenBy2.setId(1L);
        takenBy2.setName("Name");
        takenBy2.setPassword("iloveyou");
        takenBy2.setPhoneNumber("6625550144");
        takenBy2.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setId(1L);
        parkingLot2.setIsEmpty(true);
        parkingLot2.setIsVip(true);
        parkingLot2.setLayer(1);
        parkingLot2.setParking(parking2);
        parkingLot2.setTakenBy(takenBy2);
        when(parkingLotRepository.save(Mockito.<ParkingLot>any())).thenReturn(parkingLot2);
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualReleaseParkingLotResult = parkingLotService.releaseParkingLot(1L);

        // Assert
        verify(parkingLotRepository).findById(Mockito.<Long>any());
        verify(parkingLotRepository).save(Mockito.<ParkingLot>any());
        assertTrue(actualReleaseParkingLotResult);
    }

    /**
     * Method under test: {@link ParkingLotService#editLayer(Long, int)}
     */
    @Test
    void testEditLayer() {
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

        Customer takenBy = new Customer();
        takenBy.setAccountStatus("3");
        takenBy.setCity("Oxford");
        takenBy.setCountry("GB");
        takenBy.setDateOfBirth(mock(Date.class));
        takenBy.setEmail("jane.doe@example.org");
        takenBy.setId(1L);
        takenBy.setName("Name");
        takenBy.setPassword("iloveyou");
        takenBy.setPhoneNumber("6625550144");
        takenBy.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setIsVip(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTakenBy(takenBy);
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);

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

        Customer takenBy2 = new Customer();
        takenBy2.setAccountStatus("3");
        takenBy2.setCity("Oxford");
        takenBy2.setCountry("GB");
        takenBy2.setDateOfBirth(mock(Date.class));
        takenBy2.setEmail("jane.doe@example.org");
        takenBy2.setId(1L);
        takenBy2.setName("Name");
        takenBy2.setPassword("iloveyou");
        takenBy2.setPhoneNumber("6625550144");
        takenBy2.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setId(1L);
        parkingLot2.setIsEmpty(true);
        parkingLot2.setIsVip(true);
        parkingLot2.setLayer(1);
        parkingLot2.setParking(parking2);
        parkingLot2.setTakenBy(takenBy2);
        when(parkingLotRepository.save(Mockito.<ParkingLot>any())).thenReturn(parkingLot2);
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualEditLayerResult = parkingLotService.editLayer(1L, 1);

        // Assert
        verify(parkingLotRepository).findById(Mockito.<Long>any());
        verify(parkingLotRepository).save(Mockito.<ParkingLot>any());
        assertTrue(actualEditLayerResult);
    }

    /**
     * Method under test: {@link ParkingLotService#deleteParkingLot(Long)}
     */
    @Test
    void testDeleteParkingLot() {
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

        Customer takenBy = new Customer();
        takenBy.setAccountStatus("3");
        takenBy.setCity("Oxford");
        takenBy.setCountry("GB");
        takenBy.setDateOfBirth(mock(Date.class));
        takenBy.setEmail("jane.doe@example.org");
        takenBy.setId(1L);
        takenBy.setName("Name");
        takenBy.setPassword("iloveyou");
        takenBy.setPhoneNumber("6625550144");
        takenBy.setRegistrationDate(LocalDate.of(1970, 1, 1));

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setIsEmpty(true);
        parkingLot.setIsVip(true);
        parkingLot.setLayer(1);
        parkingLot.setParking(parking);
        parkingLot.setTakenBy(takenBy);
        Optional<ParkingLot> ofResult = Optional.of(parkingLot);
        doNothing().when(parkingLotRepository).delete(Mockito.<ParkingLot>any());
        when(parkingLotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualDeleteParkingLotResult = parkingLotService.deleteParkingLot(1L);

        // Assert
        verify(parkingLotRepository).delete(Mockito.<ParkingLot>any());
        verify(parkingLotRepository).findById(Mockito.<Long>any());
        assertTrue(actualDeleteParkingLotResult);
    }
}
