package com.QuickRide.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptRideDTO {
    private Long requestId;
    private Long driverId;
}