package com.parking.JustPark.repository;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.parking.JustPark.entity.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {UserRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.parking.JustPark.entity"})
@DataJpaTest
class UserRepositoryDiffblueTest {
    @Autowired
    private UserRepository userRepository;

    /**
     * Method under test: {@link UserRepository#findByEmail(String)}
     */
    @Test
    void testFindByEmail() {
        // Arrange
        Date dateOfBirth = mock(Date.class);
        when(dateOfBirth.getTime()).thenReturn(10L);

        User user = new User();
        user.setAccountStatus("3");
        user.setCountry("GB");
        user.setDateOfBirth(dateOfBirth);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setParkingList(new ArrayList<>());
        user.setPassword("iloveyou");
        user.setPhoneNumber("6625550144");
        user.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user.setRoles(new HashSet<>());
        Date dateOfBirth2 = mock(Date.class);
        when(dateOfBirth2.getTime()).thenReturn(10L);

        User user2 = new User();
        user2.setAccountStatus("Account Status");
        user2.setCountry("GBR");
        user2.setDateOfBirth(dateOfBirth2);
        user2.setEmail("john.smith@example.org");
        user2.setFirstName("John");
        user2.setLastName("Smith");
        user2.setParkingList(new ArrayList<>());
        user2.setPassword("Password");
        user2.setPhoneNumber("8605550118");
        user2.setRegistrationDate(LocalDate.of(1970, 1, 1));
        user2.setRoles(new HashSet<>());
        userRepository.save(user);
        userRepository.save(user2);

        // Act
        User actualFindByEmailResult = userRepository.findByEmail("jane.doe@example.org");

        // Assert
        verify(dateOfBirth, atLeast(1)).getTime();
        verify(dateOfBirth2, atLeast(1)).getTime();
        assertSame(user, actualFindByEmailResult);
    }
}
