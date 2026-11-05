package com.example.postgresqlreplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@BatchSize(size = 5)
@Table(name = "unit_of_measurements")
public class UnitOfMeasurement extends BaseEntity {
    @Column(nullable = false, length = 33)
    private String name;
}
