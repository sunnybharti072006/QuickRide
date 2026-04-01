package com.QuickRide.service;

import com.QuickRide.dto.AcceptRideDTO;
import com.QuickRide.dto.RideRequestDTO;
import com.QuickRide.entity.Ride;
import com.QuickRide.entity.RideRequest;
import com.QuickRide.entity.RideStatus;
import com.QuickRide.repository.RideRepository;
import com.QuickRide.repository.RideRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RideRequestRepository rideRequestRepository;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Transactional
    public RideRequest requestRide(RideRequestDTO dto) {
        RideRequest rideRequest = new RideRequest();
        rideRequest.setUserId(dto.getUserId());
        rideRequest.setPickupLat(dto.getPickupLat());
        rideRequest.setPickupLng(dto.getPickupLng());
        rideRequest.setDropLat(dto.getDropLat());
        rideRequest.setDropLng(dto.getDropLng());
        rideRequest.setStatus(RideStatus.REQUESTED);
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

    @Transactional
    public Ride acceptRide(AcceptRideDTO dto) {
        RideRequest request = rideRequestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Ride request not found"));

        if (request.getStatus() != RideStatus.REQUESTED) {
            throw new RuntimeException("Ride already accepted or invalid state");
        }

        request.setStatus(RideStatus.DRIVER_ASSIGNED);
        rideRequestRepository.save(request);

        Ride ride = new Ride();
        ride.setRequestId(request.getId());
        ride.setUserId(request.getUserId());
        ride.setDriverId(dto.getDriverId());

        ride.setStatus(RideStatus.DRIVER_ASSIGNED);
        ride.setAcceptedAt(LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);

        executor.submit(() -> log.info("Driver {} accepted ride {}", dto.getDriverId(), request.getId()));

        return savedRide;
    }

    @Transactional
    public Ride startRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.DRIVER_ASSIGNED) {
            throw new RuntimeException("Ride cannot be started from current state: " + ride.getStatus());
        }

        ride.setStatus(RideStatus.STARTED);
        ride.setStartedAt(LocalDateTime.now());

        rideRequestRepository.findById(ride.getRequestId()).ifPresent(req -> {
            req.setStatus(RideStatus.STARTED);
            rideRequestRepository.save(req);
        });

        return rideRepository.save(ride);
    }

    @Transactional
    public Ride completeRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.STARTED) {
            throw new RuntimeException("Ride not started yet, current state: " + ride.getStatus());
        }

        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());

        rideRequestRepository.findById(ride.getRequestId()).ifPresent(req -> {
            req.setStatus(RideStatus.COMPLETED);
            rideRequestRepository.save(req);
        });

        log.info("Ride ID {} completed successfully", rideId);
        return rideRepository.save(ride);
    }
}