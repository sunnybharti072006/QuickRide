package com.QuickRide.controller;


import com.QuickRide.entity.Driver;
import com.QuickRide.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    @PostMapping("/register")
    public Driver registerDriver(@RequestBody Driver driver) {
        return driverService.registerDriver(driver);
    }

    @GetMapping("/login")
    public Driver login(@RequestParam String email, @RequestParam String password) {
        return driverService.login(email, password);
    }

}
