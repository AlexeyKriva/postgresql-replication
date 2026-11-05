package com.example.postgresqlreplication.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        int id,
        String name,
        BigDecimal regularPrice,
        int salePrecent,
        int packSizeValue,
        String unitOfMeasurementName
) {
}
