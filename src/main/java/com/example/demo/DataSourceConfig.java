package com.example.demo;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
      return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
      return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("legacy.datasource")
    public DataSourceProperties legacyDataSourceProperties() {
      return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("legacy.datasource")
    public DataSource legacyDataSource() {
      return legacyDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
