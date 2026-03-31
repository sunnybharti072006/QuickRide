package com.QuickRide.controller;

import com.QuickRide.dto.AcceptRideDTO;
import com.QuickRide.dto.RideRequestDTO;
import com.QuickRide.entity.Ride;
import com.QuickRide.entity.RideRequest;
import com.QuickRide.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/request")
    public ResponseEntity<RideRequest> requestRide(@RequestBody RideRequestDTO dto) {
        return ResponseEntity.ok(rideService.requestRide(dto));
    }

    @PostMapping("/accept")
    public ResponseEntity<Ride> acceptRide(@RequestBody AcceptRideDTO dto) {
        return ResponseEntity.ok(rideService.acceptRide(dto));
    }

    @PostMapping("/start/{rideId}")
    public ResponseEntity<Ride> startRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(rideService.startRide(rideId));
    }

    @PostMapping("/complete/{rideId}")
    public ResponseEntity<Ride> completeRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(rideService.completeRide(rideId));
    }
}