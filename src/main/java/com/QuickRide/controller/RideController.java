package com.QuickRide.controller;

import com.QuickRide.dto.RideRequestDTO;
import com.QuickRide.entity.RideRequest;
import com.QuickRide.service.RideService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/request")
    public RideRequest requestRide(@RequestBody RideRequestDTO dto) {
        return rideService.requestRide(dto);
    }

}
