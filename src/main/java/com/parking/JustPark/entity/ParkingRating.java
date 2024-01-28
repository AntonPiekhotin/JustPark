package com.parking.JustPark.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_rating")
@Data
public class ParkingRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private int rating;
}
