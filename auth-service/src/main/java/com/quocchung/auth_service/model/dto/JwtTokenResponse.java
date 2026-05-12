package com.quocchung.auth_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class JwtTokenResponse {
  // token
  private String token;
   // Bearer
  private String type;
  // Thời gian token còn hiệu lực
  private String validity;
}
