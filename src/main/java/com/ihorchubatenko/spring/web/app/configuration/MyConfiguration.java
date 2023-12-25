package com.ihorchubatenko.spring.web.app.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ihorchubatenko.spring.web.app")
@EnableJpaRepositories(basePackages = "com.ihorchubatenko.spring.web.app.dao")
public class MyConfiguration {
}
