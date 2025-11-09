package com.roushan.uber.mapper;

import com.roushan.uber.dto.DriverRequest;
import com.roushan.uber.dto.DriverResponse;
import com.roushan.uber.entity.Driver;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public Driver toEntity( DriverRequest request){
        Driver driver = Driver.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .licenseNumber(request.getLicenseNumber())
                .vehiclePlateNumber(request.getVehiclePlateNumber())
                .vehicleModel(request.getVehicleModel())
                .isAvailable(request.getIsAvailable())
                .build();
        return driver;
    }

    public DriverResponse toResponse(Driver driver){
        DriverResponse response = DriverResponse.builder()
                .id(driver.getId())
                .name(driver.getName())
                .email(driver.getEmail())
                .phoneNumber( driver.getPhoneNumber())
                .licenseNumber(driver.getLicenseNumber())
                .vehicleModel(driver.getVehicleModel())
                .vehiclePlateNumber(driver.getVehiclePlateNumber())
                .isAvailable(driver.getIsAvailable())
                .createdAt(driver.getCreationAt())
                .updatedAt(driver.getUpdateAt())
                .build();
        return response;
    }

    public void updateEntity(Driver driver, DriverRequest request){
        driver.setName(request.getName());
        driver.setEmail(request.getEmail());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setLicenseNumber(request.getLicenseNumber());
        driver.setVehicleModel(request.getVehicleModel());
        driver.setVehiclePlateNumber(request.getVehiclePlateNumber());
        driver.setIsAvailable(request.getIsAvailable());
    }
}
