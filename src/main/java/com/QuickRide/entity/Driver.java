package com.QuickRide.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;

    private String vehicleNumber;
    private String vehicleType;

    private Boolean isAvailable = false;
    private Double rating = 0.0;
}