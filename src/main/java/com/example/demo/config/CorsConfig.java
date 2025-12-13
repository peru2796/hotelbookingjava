package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:4300/"
                )
                .allowedMethods("GET", "POST")
                .allowedHeaders("Content-Type", "Authorization", "X-Requested-With")
                .exposedHeaders("X-Total-Count") // if needed
                .allowCredentials(true)
                .maxAge(3600);
    }
}




