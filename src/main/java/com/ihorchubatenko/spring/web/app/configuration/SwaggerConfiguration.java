package com.ihorchubatenko.spring.web.app.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI myApiDocumentation() {
        return new OpenAPI()
                .info(new Info()
                        .title("Users API")
                        .description("Swagger 3 Documentation")
                        .version("1.0"));
    }
}