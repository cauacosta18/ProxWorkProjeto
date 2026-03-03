package com.br.proxwork.Configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")                        
            .allowedOrigins("http://localhost:5173")
            .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
            .allowedHeaders("Content-Type","Authorization","X-Requested-With","Accept")
            .exposedHeaders("Location")               
            .allowCredentials(true)                   
            .maxAge(3600);                            
    }
}
