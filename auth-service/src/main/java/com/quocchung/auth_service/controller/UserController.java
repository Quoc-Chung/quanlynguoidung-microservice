package com.quocchung.auth_service.controller;

import com.quocchung.auth_service.model.dto.JwtTokenResponse;
import com.quocchung.auth_service.model.dto.LoginRequest;
import com.quocchung.auth_service.model.dto.RegisterRequest;
import com.quocchung.auth_service.model.dto.UserDTO;
import com.quocchung.auth_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;

   @PostMapping("/register")
   public ResponseEntity<UserDTO> regesterUser(@RequestBody RegisterRequest registerRequest){
      return  new ResponseEntity<>(userService.registerUser(registerRequest), HttpStatus.CREATED);
   }

   @PostMapping("/login")
   public ResponseEntity<JwtTokenResponse> login(@RequestBody @Valid LoginRequest loginRequest){
      return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
   }


}
