package com.hanghae.project.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MysqlConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
