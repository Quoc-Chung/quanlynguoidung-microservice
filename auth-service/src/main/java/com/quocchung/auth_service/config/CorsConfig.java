package com.quocchung.auth_service.config;

import com.quocchung.auth_service.utils.CorsProperties;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

  private final CorsProperties corsProperties;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();

    // Origins từ yml theo profile
    config.setAllowedOrigins(corsProperties.getAllowedOrigins());

    // Các method cho phép
    config.setAllowedMethods(Arrays.asList(
        "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
    ));

    // Các header cho phép
    config.setAllowedHeaders(Arrays.asList(
        "Authorization",
        "Content-Type",
        "Accept",
        "X-Requested-With"
    ));

    // Cho phép gửi cookie / Authorization header
    config.setAllowCredentials(true);

    // Cache preflight request trong 1 giờ
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config); // áp dụng cho tất cả endpoint

    return source;
  }
}