package com.quocchung.gateway_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quocchung.gateway_service.Exception.ErrorResponse;
import com.quocchung.gateway_service.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

  private final Validator validator;
  private final ObjectMapper objectMapper;
  private final JwtUtils jwtUtils;

  public AuthFilter(Validator validator, ObjectMapper objectMapper, JwtUtils jwtUtils) {
    super(Config.class);
    this.validator = validator;
    this.objectMapper = objectMapper;
    this.jwtUtils = jwtUtils;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {

      String path = exchange.getRequest().getURI().getPath();

      // Public API thì cho đi qua luôn
      if (!validator.isSecured(path)) {
        return chain.filter(exchange);
      }

      // Protected API thì kiểm tra token
      String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED,
            "Authorization header is missing bearer token.");
      }

      String token = authHeader.substring(7);

      try {
        String username = jwtUtils.extractUsername(token);
        if (username == null || jwtUtils.isTokenExpired(token)) {
          return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token không hợp lệ");
        }

        // Gắn thông tin user vào header để downstream service sử dụng
        ServerWebExchange mutatedExchange = exchange.mutate()
            .request(exchange.getRequest().mutate()
                .header("X-User-Name", username)
                .build())
            .build();

        return chain.filter(mutatedExchange);

      } catch (ExpiredJwtException e) {
        log.error("Token hết hạn: {}", e.getMessage());
        return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token đã hết hạn");
      } catch (MalformedJwtException | UnsupportedJwtException e) {
        log.error("Token không hợp lệ: {}", e.getMessage());
        return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token không hợp lệ");
      } catch (Exception e) {
        log.error("Lỗi xác thực token: {}", e.getMessage());
        return writeErrorResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi xác thực token");
      }
    };
  }

  public static class Config {
  }

  private Mono<Void> writeErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .message(message)
        .status(status)
        .timestamp(LocalDateTime.now())
        .build();

    exchange.getResponse().setStatusCode(status);
    exchange.getResponse().getHeaders().add("Content-Type", "application/json");

    try {
      byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
      return exchange.getResponse().writeWith(Mono.just(buffer));
    } catch (Exception e) {
      return exchange.getResponse().setComplete();
    }
  }
}