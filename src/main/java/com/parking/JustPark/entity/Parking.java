package com.parking.JustPark.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parkings")
@Data
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingLot> parkingLots = new ArrayList<>();

    private String title;
    private String address;

    @Column(name = "price_default")
    private double priceDefault;

    @Column(name = "price_vip")
    private double priceVip;

}
