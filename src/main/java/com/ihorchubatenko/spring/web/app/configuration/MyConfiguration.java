package com.ihorchubatenko.spring.web.app.configuration;

import com.ihorchubatenko.spring.web.app.dao.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ihorchubatenko.spring.web.app")
public class MyConfiguration {

    @Bean
    public Map<Long, User> userDatabase() {
        return new HashMap<>();
    }

    @Bean
    public AtomicLong userIdGenerator() {
        return new AtomicLong(1);
    }

}
