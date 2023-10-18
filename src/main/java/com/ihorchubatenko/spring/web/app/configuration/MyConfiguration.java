package com.ihorchubatenko.spring.web.app.configuration;

import com.ihorchubatenko.spring.web.app.controller.UsersController;
import com.ihorchubatenko.spring.web.app.entity.UserRepository;
import com.ihorchubatenko.spring.web.app.entity.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ihorchubatenko.spring.web.app")
public class MyConfiguration {

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl();
    }

    @Bean
    public UsersController myController(UserRepository userRepository){
        return new UsersController(userRepository);
    }

}
