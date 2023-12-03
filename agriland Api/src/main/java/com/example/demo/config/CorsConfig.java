package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Add the origin of your React app
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Add the allowed HTTP methods
                .allowedHeaders("*") // Add the allowed headers
                .allowCredentials(true) // Allow including credentials in cross-origin requests
                .maxAge(3600); // Max age for CORS preflight requests
    }
}
