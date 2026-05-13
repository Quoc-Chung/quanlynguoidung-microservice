package com.quocchung.auth_service.service.impl;


import com.quocchung.auth_service.model.dto.JwtTokenResponse;
import com.quocchung.auth_service.model.dto.LoginRequest;
import com.quocchung.auth_service.model.dto.RegisterRequest;
import com.quocchung.auth_service.model.dto.UserDTO;
import com.quocchung.auth_service.model.entity.User;
import com.quocchung.auth_service.repository.UserRepository;
import com.quocchung.auth_service.service.MyUserDetailsService;
import com.quocchung.auth_service.service.UserService;
import com.quocchung.auth_service.utils.JwtUtils;
import com.quocchung.common_lib.Exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final MyUserDetailsService userDetailsService;
  private final JwtUtils jwtUtils;

  @Override
  public UserDTO registerUser(RegisterRequest registerRequest) {
    if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
      throw new CustomException("User rearly exists with email : " + registerRequest.getEmail());
    }
    if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
      throw new CustomException("User rearly exists with usename: "+ registerRequest.getUsername());
    }

    User user  = User.builder()
        .username(registerRequest.getUsername())
        .firstName(registerRequest.getFirstName())
        .lastName(registerRequest.getLastName())
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword()))
        .roles(registerRequest.getRoles())
        .build();
    UserDTO userDTO  = saveUser(user);
    return userDTO;
  }

  @Override
  public JwtTokenResponse login(LoginRequest loginRequest) {
    // Xác thực username/password → throw exception nếu sai
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(), loginRequest.getPassword()
    ));

    // Load user từ DB
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
    String token = jwtUtils.generateToken(userDetails);

    // Tính thời gian còn hiệu lực
    long expirationMs = jwtUtils.getExpirationTime();
    String validity = expirationMs / 1000 / 3600 + " hours";

    return JwtTokenResponse.builder()
        .token(token)
        .type("Bearer")
        .validity(validity)
        .build();
  }

  public UserDTO saveUser(User user){
      userRepository.save(user);

      return UserDTO.builder()
          .id(user.getId())
          .emai(user.getEmail())
          .username(user.getUsername())
          .roles(user.getRoles())
          .build();
  }

}
