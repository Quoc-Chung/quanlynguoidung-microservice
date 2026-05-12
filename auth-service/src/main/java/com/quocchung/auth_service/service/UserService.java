package com.quocchung.auth_service.service;

import com.quocchung.auth_service.model.dto.JwtTokenResponse;
import com.quocchung.auth_service.model.dto.LoginRequest;
import com.quocchung.auth_service.model.dto.RegisterRequest;
import com.quocchung.auth_service.model.dto.UserDTO;

public interface UserService {

    UserDTO registerUser(RegisterRequest registerRequest);

    JwtTokenResponse login(LoginRequest loginRequest);

}
