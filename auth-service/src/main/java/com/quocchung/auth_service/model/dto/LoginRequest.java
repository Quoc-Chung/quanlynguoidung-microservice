package com.quocchung.auth_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

  @NotBlank(message = "Username không được để trống")
  private String username;

  @NotBlank(message = "Password không được để trống")
  @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
  private String password;

}
