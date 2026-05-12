package com.quocchung.auth_service.utils;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cors")
@Data
public class CorsProperties {
  private List<String> allowedOrigins;
}