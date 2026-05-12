package com.quocchung.employee_service.Exception;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
     ErrorResponse errorResponse = new ErrorResponse(ex.getMessage().toString(), ex.getStatus() );
     return new ResponseEntity<>(errorResponse, ex.getStatus());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex){
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage().toString(), ex.getHttpStatus());
    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  @ExceptionHandler(MissingParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParameterException(MissingParameterException ex){
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage().toString(), ex.getStatus());
    return new ResponseEntity<>(errorResponse, ex.getStatus());
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    ErrorResponse error = ErrorResponse.builder()
        .message(ex.getMessage())
        .status(ex.getHttpStatus())
        .timestamp(LocalDateTime.now())
        .build();
    return ResponseEntity.status(ex.getHttpStatus()).body(error);
  }

}
