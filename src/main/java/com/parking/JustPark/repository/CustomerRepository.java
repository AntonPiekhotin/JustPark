package com.parking.JustPark.repository;

import com.parking.JustPark.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByEmail(String email);
}
