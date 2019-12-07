package com.authskull.demo.config;

import com.authskull.demo.utils.Helper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Helper helper() {
        return new Helper();
    }
}
