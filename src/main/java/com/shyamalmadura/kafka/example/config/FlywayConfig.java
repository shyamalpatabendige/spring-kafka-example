package com.shyamalmadura.kafka.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FlywayConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        log.info("Flyway migration is done with application start up 😎 🏁 ↔️");
        return Flyway.configure()
                .dataSource(dataSource())
                .locations("classpath:db/migration")
                .load();
    }

    public DataSource dataSource() {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
