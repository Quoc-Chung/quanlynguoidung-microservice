package com.quocchung.gateway_service.Exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex){
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage().toString(), ex.getHttpStatus());
    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
    ErrorResponse errorResponse = ErrorResponse.builder()
        .message("Lỗi hệ thống, vui lòng thử lại sau")
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .timestamp(LocalDateTime.now())
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
