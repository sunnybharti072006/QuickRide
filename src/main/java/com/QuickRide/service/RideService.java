package com.QuickRide.service;

import com.QuickRide.dto.RideRequestDTO;
import com.QuickRide.entity.RideRequest;
import com.QuickRide.repository.RideRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.QuickRide.dto.AcceptRideDTO;
import com.QuickRide.entity.Ride;
import com.QuickRide.repository.RideRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RideRequestRepository rideRequestRepository;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public RideRequest requestRide(RideRequestDTO dto) {
        RideRequest rideRequest = new RideRequest();
        rideRequest.setUserId(dto.getUserId());
        rideRequest.setPickupLat(dto.getPickupLat());
        rideRequest.setPickupLng(dto.getPickupLng());
        rideRequest.setDropLat(dto.getDropLat());
        rideRequest.setDropLng(dto.getDropLng());
        rideRequest.setStatus("REQUESTED");
        rideRequest.setRequestedAt(LocalDateTime.now());

        RideRequest savedRequest = rideRequestRepository.save(rideRequest);

        executor.submit(() -> {
            try {
                log.info("Finding driver for ride request ID: {}", savedRequest.getId());
            } catch (Exception e) {
                log.error("Error during driver matching", e);
            }
        });

        return savedRequest;
    }

    public Ride acceptRide(AcceptRideDTO dto) {
        RideRequest request = rideRequestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Ride request not found"));

        if (!"REQUESTED".equals(request.getStatus())) {
            throw new RuntimeException("Ride already accepted or invalid state");
        }

        request.setStatus("DRIVER_ASSIGNED");
        rideRequestRepository.save(request);

        Ride ride = new Ride();
        ride.setRequestId(request.getId());
        ride.setUserId(request.getUserId());
        ride.setDriverId(dto.getDriverId());
        ride.setStatus("ACCEPTED");
        ride.setAcceptedAt(LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);

        executor.submit(() -> {
            log.info("Driver {} accepted ride {}", dto.getDriverId(), request.getId());
        });

        return savedRide;
    }
}