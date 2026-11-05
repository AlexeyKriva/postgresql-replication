package com.example.postgresqlreplication.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "read-replica.datasource")
public record ReadDataSourceProperties (
        String url,
        String driverClassName,
        String username,
        String password,
        HikariProperties hikari
) {
}
