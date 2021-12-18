package com.berk2s.talent.productapi.configuration;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakerConfiguration {

    @Bean
    Faker faker() {
        return new Faker();
    }

}
