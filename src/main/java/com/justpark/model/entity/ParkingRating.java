package com.justpark.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "parking_rating")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParkingRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    Parking parking;

    Integer ratingNumber;

}
