package com.parking.JustPark.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_lots")
@Data
public class ParkingLot {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    private Parking parking;

    @Column(name = "is_empty")
    private Boolean isEmpty;

    private Integer layer;

    @Column(name = "is_vip")
    private Boolean isVip;

    //TODO
    // add takenByCustomer field

}
