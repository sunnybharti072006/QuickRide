package com.QuickRide.controller;

import com.QuickRide.dto.LocationUpdateDTO;
import com.QuickRide.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TrackingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private TrackingService trackingService;

    @MessageMapping("/driver-location")
    public void processLocation(@Payload LocationUpdateDTO data) {
        trackingService.updateDriverInRedis(data.getDriverId(), data.getLat(), data.getLng());

        double distance = trackingService.getLiveDistance(data.getDriverId(), data.getUserId());

        String destination = "/topic/distance/" + data.getUserId();
        String message = String.format("%.2f KM away", distance);

        messagingTemplate.convertAndSend(destination, message);
    }
}