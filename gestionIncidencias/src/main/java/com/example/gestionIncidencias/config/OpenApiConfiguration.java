package com.example.gestionIncidencias.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI documentacionOpenApi(){
        return new OpenAPI()
        .info(new Info()
        .title("Gestion de incidencias")
        .version("1.1")
        .description("Agrega incidencias")
        .contact(new Contact()
            .name("Ale")
            .email("edutech@hmail.com")
            .url("eduApi.cl")));
    }
}
