package com.example.postgresqlreplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_base_pack_sizes")
public class ProductBasePackSize extends BaseEntity {
    @Column(nullable = false)
    private int value;

    @ManyToOne(optional = false)
    private UnitOfMeasurement unitOfMeasurement;
}
