package com.quocchung.auth_service.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BadRequestException extends RuntimeException{
   private String message;
   private HttpStatus httpStatus;

   public BadRequestException(String message){
     this.message = message;
     this.httpStatus = HttpStatus.BAD_REQUEST;
   }
}
