package com.QuickRide.service;

import com.QuickRide.entity.GpsLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrackingService {

    private final RedisTemplate<String, Object> redisTemplate;

    public TrackingService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void updateDriverLocation(String driverId, double lat, double lng) {
        GpsLocation loc = new GpsLocation(lat, lng);
        redisTemplate.opsForValue().set("driver_loc:" + driverId, loc);
    }

    public GpsLocation getUserLocation(String userId) {
        Object obj = redisTemplate.opsForValue().get("user_loc:" + userId);
        return (obj instanceof GpsLocation) ? (GpsLocation) obj : null;
    }

    public double getLiveDistance(String userId, double dLat, double dLng) {
        GpsLocation userLoc = getUserLocation(userId);
        if (userLoc == null) return -1;

        return calculateHaversine(dLat, dLng, userLoc.getLat(), userLoc.getLng());
    }

    private double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}