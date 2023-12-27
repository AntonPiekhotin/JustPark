package com.parking.JustPark.repository;

import com.parking.JustPark.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    public List<User> findAllByOrderById();
}
