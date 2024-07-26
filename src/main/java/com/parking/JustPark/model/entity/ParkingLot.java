package com.parking.JustPark.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_lots")
@Data
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private Parking parking;

    @Column(name = "is_empty")
    private Boolean isEmpty;

    private Integer layer;

    @Column(name = "is_vip")
    private Boolean isVip;

}
