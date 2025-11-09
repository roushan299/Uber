package com.roushan.uber.controller;

import com.roushan.uber.dto.DriverRequest;
import com.roushan.uber.dto.DriverResponse;
import com.roushan.uber.services.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers(){
       List<DriverResponse> driverList = driverService.findAll();
       return ResponseEntity.ok().body(driverList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getByDriverId(@PathVariable Long id){
       return driverService.findById(id)
               .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DriverResponse> getByEmail(@PathVariable String email){
        return driverService.findByIdEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<DriverResponse>> getAvailableDrivers(){
        List<DriverResponse> driverList = driverService.findAvailableDrivers();
        return ResponseEntity.ok().body(driverList);
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createPassenger(@Valid @RequestBody DriverRequest driverRequest){
        try {
            DriverResponse driverResponse  = driverService.create(driverRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(driverResponse);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updatePassenger(@PathVariable Long id, @Valid @RequestBody DriverRequest driverRequest){
        try {
            DriverResponse driverResponse = driverService.update(id, driverRequest);
            return ResponseEntity.ok().body(driverResponse);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id){
        try {
            driverService.deleteById(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}
