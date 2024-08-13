package com.justpark.model.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parkings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    User owner;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ParkingLot> parkingLots = new ArrayList<>();

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ParkingRating> ratings = new ArrayList<>();

    String title;
    String address;
    String city;

    @Column(name = "price_per_hour")
    BigDecimal pricePerHour;

}
