package com.parking.JustPark.repository;

import com.parking.JustPark.entity.Customer;
import com.parking.JustPark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByEmail(String email);
}
