package com.parking.JustPark.entity;

import javax.persistence.*;

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

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//    private List<Role> roles = new ArrayList<>();

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
