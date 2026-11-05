package com.example.postgresqlreplication.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {
    public static final String WRITE_LOOKUP_KEY = "WRITE";
    public static final String READ_LOOKUP_KEY = "READ";

    public static class Exception {
        public static final String PRODUCT_NOT_FOUND_MESSAGE_TEMPLATE = "Product was not found with id %d";
    }
}
