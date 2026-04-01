package com.QuickRide.dto;

import lombok.Data;

@Data
public class LocationUpdateDTO {
    private String driverId;
    private String userId;
    private double lat;
    private double lng;
}
