package com.example.postgresqlreplication.config.properties;

public record HikariProperties (
        int maximumPoolSize,
        int minimumIdle,
        int connectionTimeout,
        int keepaliveTime,
        int leakDetectionThreshold
) {
}
