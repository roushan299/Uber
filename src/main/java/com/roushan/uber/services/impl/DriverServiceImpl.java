package com.roushan.uber.services.impl;

import com.roushan.uber.dto.DriverRequest;
import com.roushan.uber.dto.DriverResponse;
import com.roushan.uber.entity.Driver;
import com.roushan.uber.mapper.DriverMapper;
import com.roushan.uber.repository.DriverRepository;
import com.roushan.uber.services.DriverService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<DriverResponse> findByIdEmail(String email) {
        return driverRepository.findByEmail(email)
                .map(driverMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponse> findAvailableDrivers() {
        return driverRepository.findAll().stream()
                .filter(Driver::getIsAvailable)
                .map(driverMapper::toResponse)
                .collect( Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DriverResponse> findById(Long id) {
        return driverRepository.findById(id)
                .map(driverMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverResponse> findAll() {
        return driverRepository.findAll().stream()
                .map(driverMapper::toResponse)
                .collect( Collectors.toList());
    }

    @Override
    public DriverResponse create(DriverRequest request) {
       if(driverRepository.exitsByEmail(request.getEmail())){
           throw new IllegalArgumentException("Driver with email "+request.getEmail() +" already exits");
       }

       if(driverRepository.exitsByLicenseNumber(request.getLicenseNumber())){
           throw new IllegalArgumentException("Driver with license number "+request.getLicenseNumber()+" already exits");
       }
       Driver driver = driverMapper.toEntity(request);
       Driver savedDriver = driverRepository.save(driver);
       return driverMapper.toResponse(savedDriver);
    }

    @Override
    public DriverResponse update(Long id, DriverRequest request) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: "+ id));

        if(!driver.getEmail().equals(request.getEmail()) && driverRepository.exitsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Driver with email "+request.getEmail() +" already exits");
        }

        if(!driver.getLicenseNumber().equals(request.getLicenseNumber()) && driverRepository.exitsByLicenseNumber(request.getLicenseNumber())){
            throw new IllegalArgumentException("Driver with license number "+request.getLicenseNumber()+" already exits");
        }

        driverMapper.updateEntity(driver, request);
        Driver updatedDriver = driverRepository.save(driver);
        return driverMapper.toResponse(updatedDriver);
    }

    @Override
    public void deleteById(Long id) {
       if(!driverRepository.existsById(id)){
           throw new IllegalArgumentException("Driver not found with id: "+id);
       }
       driverRepository.deleteById(id);
    }

}
