package com.example.postgresqlreplication.mapper;

import com.example.postgresqlreplication.dto.ProductResponse;
import com.example.postgresqlreplication.entity.Product;
import com.example.postgresqlreplication.entity.ProductBasePackSize;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.postgresqlreplication.util.ProductPriceUtil.getRegularPrice;

@Component
public class ProductMapper {
    public List<ProductResponse> toProductResponses(List<Product> products) {
        return products.stream()
                .map(this::toProductResponse)
                .toList();
    }

    public ProductResponse toProductResponse(Product product) {
        ProductBasePackSize packSize = product.getPackSize();

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .salePrecent(product.getSalePrecent())
                .regularPrice(getRegularPrice(product))
                .packSizeValue(packSize.getValue())
                .unitOfMeasurementName(packSize.getUnitOfMeasurement().getName())
                .build();
    }
}
