package com.example.postgresqlreplication.repository;

import com.example.postgresqlreplication.entity.UnitOfMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitOfMeasurementRepo extends JpaRepository<UnitOfMeasurement, Integer> {
}
