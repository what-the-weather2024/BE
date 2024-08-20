package com.weather.the.what.whattheweather.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*") //TODO: 특정 도메인만 허용
        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
        .allowCredentials(true);
  }
}
