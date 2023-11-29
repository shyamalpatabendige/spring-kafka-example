package com.shyamalmadura.kafka.example.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakerConfig {
    @Bean
    public Faker getBean(){
        return new Faker();
    }
}
