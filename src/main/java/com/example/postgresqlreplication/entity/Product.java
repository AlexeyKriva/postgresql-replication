package com.example.postgresqlreplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private ProductBasePackSize packSize;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, length = 200)
    private int salePrecent;

    @Formula("""
                CASE
                    WHEN sale_precent IS NOT NULL
                    THEN price * sale_precent
                    ELSE NULL
                END
            """)
    private BigDecimal salePrice;
}
