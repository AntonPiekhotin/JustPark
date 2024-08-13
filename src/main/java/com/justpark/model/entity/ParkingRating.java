package com.justpark.model.entity;

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

    private int rating;
}
