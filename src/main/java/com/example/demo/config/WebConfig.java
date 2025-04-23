package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Цей URL буде працювати як доступ до файлової системи
        registry.addResourceHandler("/uploads/avatar/**", "/uploads/books/**")
                .addResourceLocations("file:uploads/avatar/", "file:uploads/books/");
    }
}
