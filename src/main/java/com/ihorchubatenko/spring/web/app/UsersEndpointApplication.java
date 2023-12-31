package com.ihorchubatenko.spring.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ihorchubatenko.spring.web.app.entity")
public class UsersEndpointApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersEndpointApplication.class, args);
    }
}
