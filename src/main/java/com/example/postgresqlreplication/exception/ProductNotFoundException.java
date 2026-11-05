package com.example.postgresqlreplication.exception;

import lombok.Getter;

import static com.example.postgresqlreplication.util.AppConstants.Exception.PRODUCT_NOT_FOUND_MESSAGE_TEMPLATE;

@Getter
public class ProductNotFoundException extends AppException{
    private final int errorId;

    public ProductNotFoundException(int errorId) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE_TEMPLATE, errorId));
        this.errorId = errorId;
    }
}
