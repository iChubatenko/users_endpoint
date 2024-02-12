package com.ihorchubatenko.spring.web.app;

import com.ihorchubatenko.spring.web.app.controller.UsersController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ihorchubatenko.spring.web.app.entity")
public class UsersEndpointApplication {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    public static void main(String[] args) {
        SpringApplication.run(UsersEndpointApplication.class, args);
        logger.info("The application is launched and ready to use");
    }
}
