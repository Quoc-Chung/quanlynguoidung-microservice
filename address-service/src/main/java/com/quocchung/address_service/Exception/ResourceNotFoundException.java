package com.quocchung.address_service.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResourceNotFoundException  extends  RuntimeException{

  private String message;
  private HttpStatus status;

  public ResourceNotFoundException(String message, HttpStatus httpStatus) {
    this.message = message;
    this.status = httpStatus;
  }

  public ResourceNotFoundException(String message) {
    this.message = message;
    this.status = HttpStatus.NOT_FOUND;
  }
}
