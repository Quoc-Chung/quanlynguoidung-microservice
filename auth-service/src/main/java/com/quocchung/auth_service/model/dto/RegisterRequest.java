package com.quocchung.auth_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
  private String firstName;

  private String lastName;

  private String email;

  private String username;

  private String password;

  private String roles;
}
