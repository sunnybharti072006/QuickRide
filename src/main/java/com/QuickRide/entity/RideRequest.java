package com.QuickRide.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride_requests")
@Getter
@Setter
public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private double pickupLat;
    private double pickupLng;
    private double dropLat;
    private double dropLng;

    private String status;
    private LocalDateTime requestedAt;
}
