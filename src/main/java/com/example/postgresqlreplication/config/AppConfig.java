package com.example.postgresqlreplication.config;

import com.example.postgresqlreplication.config.properties.ReadDataSourceProperties;
import com.example.postgresqlreplication.config.properties.WriteDataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties(value = {ReadDataSourceProperties.class, WriteDataSourceProperties.class})
public class AppConfig {
}
