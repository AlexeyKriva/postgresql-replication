package com.example.postgresqlreplication.config;

import com.example.postgresqlreplication.config.properties.ReadDataSourceProperties;
import com.example.postgresqlreplication.config.properties.WriteDataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

import static com.example.postgresqlreplication.util.AppConstants.READ_LOOKUP_KEY;
import static com.example.postgresqlreplication.util.AppConstants.WRITE_LOOKUP_KEY;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    @Bean
    public DataSource writeDataSource(WriteDataSourceProperties writeDataSourceProperties) {
        HikariDataSource hikariDataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .username(writeDataSourceProperties.username())
                .password(writeDataSourceProperties.password())
                .driverClassName(writeDataSourceProperties.driverClassName())
                .url(writeDataSourceProperties.url())
                .build();

        hikariDataSource.setMaximumPoolSize(writeDataSourceProperties.hikari().maximumPoolSize());
        hikariDataSource.setMinimumIdle(writeDataSourceProperties.hikari().minimumIdle());
        hikariDataSource.setConnectionTimeout(writeDataSourceProperties.hikari().connectionTimeout());
        hikariDataSource.setKeepaliveTime(writeDataSourceProperties.hikari().keepaliveTime());
        hikariDataSource.setLeakDetectionThreshold(writeDataSourceProperties.hikari().leakDetectionThreshold());

        return hikariDataSource;
    }

    @Bean
    public DataSource readDataSource(ReadDataSourceProperties readDataSourceProperties) {
        HikariDataSource hikariDataSource = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .username(readDataSourceProperties.username())
                .password(readDataSourceProperties.password())
                .driverClassName(readDataSourceProperties.driverClassName())
                .url(readDataSourceProperties.url())
                .build();

        hikariDataSource.setMaximumPoolSize(readDataSourceProperties.hikari().maximumPoolSize());
        hikariDataSource.setMinimumIdle(readDataSourceProperties.hikari().minimumIdle());
        hikariDataSource.setConnectionTimeout(readDataSourceProperties.hikari().connectionTimeout());
        hikariDataSource.setKeepaliveTime(readDataSourceProperties.hikari().keepaliveTime());
        hikariDataSource.setLeakDetectionThreshold(readDataSourceProperties.hikari().leakDetectionThreshold());

        return hikariDataSource;
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource") DataSource readDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        routingDataSource.setTargetDataSources(Map.of(
                WRITE_LOOKUP_KEY, writeDataSource,
                READ_LOOKUP_KEY, readDataSource
        ));
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(
            @Qualifier("routingDataSource") DataSource routingDataSource
    ) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
