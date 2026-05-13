package com.quocchung.common_lib.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException{
  private HttpStatus httpStatus;

  public CustomException(String message, HttpStatus status){
    super(message);
    this.httpStatus = status;
  }

  public CustomException(String message){
    super(message);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
