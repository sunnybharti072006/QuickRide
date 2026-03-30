package com.QuickRide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDTO {
    private Long userId;
    private double pickupLat;
    private double pickupLng;
    private double dropLat;
    private double dropLng;
}