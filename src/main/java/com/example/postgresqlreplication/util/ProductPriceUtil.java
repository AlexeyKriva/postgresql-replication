package com.example.postgresqlreplication.util;

import com.example.postgresqlreplication.entity.Product;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ProductPriceUtil {
    public static boolean isWithSale(Product product) {
        return product.getSalePrecent() > 0;
    }

    public static BigDecimal getRegularPrice(Product product) {
        return isWithSale(product)
                ? product.getSalePrice()
                : product.getPrice()
                ;
    }
}
