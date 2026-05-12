package com.quocchung.auth_service.Exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
  private String message;
  private HttpStatus status;
  private LocalDateTime timestamp;
  private Map<String, String> errors;

  public ErrorResponse(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
    timestamp =LocalDateTime.now();
  }
}
