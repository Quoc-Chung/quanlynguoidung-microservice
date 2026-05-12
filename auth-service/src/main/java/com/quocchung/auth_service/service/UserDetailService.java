package com.quocchung.auth_service.service;

import com.quocchung.auth_service.model.entity.User;
import java.util.Arrays;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailService implements UserDetails {
  private User user;
  // LẤY RA QUYỀN
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).toList();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }
}
