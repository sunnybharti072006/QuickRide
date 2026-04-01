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
        trackingService.updateDriverLocation(data.getDriverId(), data.getLat(), data.getLng());

        double distance = trackingService.getLiveDistance(
                data.getUserId(),    // 1st: String userId
                data.getLat(),       // 2nd: double dLat
                data.getLng()        // 3rd: double dLng
        );

        if (distance != -1) {
            String topic = "/topic/distance/" + data.getUserId();
            String message = String.format("%.2f KM", distance);
            messagingTemplate.convertAndSend(topic, message);
        }
    }
}