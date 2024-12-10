package com.appopay.aml.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
//                .allowedOrigins("https://aml.appopay.com")
//                .allowedOrigins("https://aml-backend.appopay.com")
//                .allowedOrigins("https://aml-backend.chenchenapp.com")// Explicitly allow your Angular app's URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
//                .allowCredentials(true);  // If you're sending credentials like cookies or Authorization tokens
    }
}
