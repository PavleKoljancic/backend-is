package com.app.backend.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.backend.models.Driver;
import com.app.backend.repositories.DriverRepo;

@Service
public class DriverService implements UserDetailsService{
    
    @Autowired
    DriverRepo driverRepo;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        Driver user = driverRepo.findByPin(pin);
        if(user.getIsActive()){
            List<String> roles = List.of("DRIVER");
            org.springframework.security.core.userdetails.User tmp = new org.springframework.security.core.userdetails.User(user.getPin(), user.getPassword(), mapRoleToAuhtorities(roles));
            return tmp;
        }
        else
            return null;
    }

    private Collection<GrantedAuthority> mapRoleToAuhtorities(List<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
    
    public Integer registerDriver(Driver driver) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        driver.setPassword(passwordEncoder.encode(driver.getPin()));
        return driverRepo.save(driver).getId();
    }

    public Optional<Driver> getById(Integer driverId){

        return driverRepo.findById(driverId);
    }

    public Driver findByPin(String pin){

        return driverRepo.findByPin(pin);
    }

    public List<Driver> getDriversByTransporterId(Integer transporterId, PageRequest pageRequest ){

        if(transporterId != null)
            return driverRepo.findAllByTransporterId(transporterId, pageRequest);
        else
            return null;
    }

    public List<Driver> getActiveDrivers(Integer transporterId, PageRequest pageRequest ){

        if(transporterId != null)
            return driverRepo.findAllByTransporterIdAndIsActiveTrue(transporterId, pageRequest);
        else
            return null;
    }

    public List<Driver> getInactiveDrivers(Integer transporterId, PageRequest pageRequest ){

        if(transporterId != null)
            return driverRepo.findAllByTransporterIdAndIsActiveFalse(transporterId, pageRequest);
        else
            return null;
    }

    public boolean ChangeIsActiveDriverId(Integer driverId, Boolean isActive) {
        
        Optional<Driver> result = driverRepo.findById(driverId);
        if(result.isPresent() && result.get().getIsActive() != isActive)
        {
            Driver temp = result.get();
            temp.setIsActive(isActive);
            driverRepo.save(temp);
            return true;
        }
        return false;
    }
}
