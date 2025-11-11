package com.roushan.uber.repository;

import com.roushan.uber.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByLicenseNumber(String licenseNumber);
    boolean exitsByEmail(String email);
    boolean exitsByLicenseNumber(String licenseNumber);

}
