package com.example.postgresqlreplication.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public record WriteDataSourceProperties(
        String url,
        String driverClassName,
        String username,
        String password,
        HikariProperties hikari
) {
}
