package com.quocchung.gateway_service.controller;

import com.quocchung.common_lib.Exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
/**
 * Thông thường dùng để lỗi xử lý máy chủ nội bộ
 * Còn khi gọi từ service này sang service kia thì cái này không bắt được
 */
public class FallbackController {

  @RequestMapping("/auth")
  public Mono<ResponseEntity<ErrorResponse>> authFallback() {
    ErrorResponse error = new ErrorResponse(
        "Auth Service không khả dụng. Vui lòng thử lại sau",
        HttpStatus.SERVICE_UNAVAILABLE  // 503
    );
    return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error));
  }

  @RequestMapping("/employee")
  public Mono<ResponseEntity<ErrorResponse>> employeeFallback() {
    ErrorResponse error = new ErrorResponse(
        "Employee Service không khả dụng. Vui lòng thử lại sau",
        HttpStatus.SERVICE_UNAVAILABLE
    );
    return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error));
  }

  @RequestMapping("/address")
  public Mono<ResponseEntity<ErrorResponse>> addressFallback() {
    ErrorResponse error = new ErrorResponse(
        "Address Service không khả dụng. Vui lòng thử lại sau",
        HttpStatus.SERVICE_UNAVAILABLE
    );
    return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error));
  }
}