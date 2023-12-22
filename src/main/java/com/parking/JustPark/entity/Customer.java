package com.parking.JustPark.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String name;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "registration_date")
    private Date registrationDate;

    private String country;

    @Column(name = "account_status")
    private String accountStatus;
}
