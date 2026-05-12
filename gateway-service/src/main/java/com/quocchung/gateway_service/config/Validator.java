package com.quocchung.gateway_service.config;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 * Kiểm tra request hiện tại có phải public API hay không.
 */
@Component
public class Validator {

  private final AntPathMatcher antPathMatcher =
      new AntPathMatcher();

  public static final List<String> endpoints =
      List.of(
          "/api/auth/login",
          "/api/auth/register",
          "/api/auth/refresh-token",
          "/api/validate-token/{token}"
      );
  public boolean isSecured(String path) {

    return endpoints.stream()
        .noneMatch(pattern ->
            antPathMatcher.match(pattern, path)
        );
  }
}