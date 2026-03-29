package com.QuickRide.service;


import com.QuickRide.entity.Driver;
import com.QuickRide.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver registerDriver(Driver driver) {
        return driverRepository.save(driver);
    }
    public Driver login(String email, String password) {
        Optional<Driver> driver = driverRepository.findDriverByEmail(email);

        if (driver.isPresent() && driver.get().getPassword().equals(password)) return driver.get();
        else throw new UsernameNotFoundException("Invalid email or password");
    }
}
