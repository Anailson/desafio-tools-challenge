package com.anailson.tools_challenge.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de pagamentos")
                        .description("API desenvolvida para o desafio técnico.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Anailson Ribeiro")
                                .email("arsantos315@gmail.com")
                                .url("https://www.linkedin.com/in/anailson-ribeiro-257a0229/")));
    }
}
