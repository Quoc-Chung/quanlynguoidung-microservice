package com.quocchung.auth_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quocchung.auth_service.Exception.ErrorResponse;
import com.quocchung.auth_service.service.MyUserDetailsService;
import com.quocchung.auth_service.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtils;
  private final MyUserDetailsService userDetailsService;
  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    // Tức là nếu các API không cần token thì cho nó đi qua luôn
    if(authHeader == null || !authHeader.startsWith("Bearer ")){
      filterChain.doFilter(request,response);
      return;
    }
    // Đói với các API truyền với header lên thì cần kiểm chứng token
    try{

      final String token = authHeader.substring(7);
      final String username = jwtUtils.extractUsername(token);

      // Truyền token lên nhưng chưa đăng nhập
      if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtUtils.isTokenValid(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities()
              );
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
    }catch (ExpiredJwtException e) {
      log.error("Token hết hạn: {}", e.getMessage());
      writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token đã hết hạn");
      return;

    } catch (MalformedJwtException e) {
      log.error("Token sai định dạng: {}", e.getMessage());
      writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token không hợp lệ");
      return;
    } catch (UnsupportedJwtException e) {
      log.error("Token không được hỗ trợ: {}", e.getMessage());
      writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token không được hỗ trợ");
      return;

    } catch (Exception e) {
      log.error("Lỗi xác thực token: {}", e.getMessage());
      writeErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi xác thực token");
      return;
    }
    filterChain.doFilter(request, response);
  }

  private void writeErrorResponse(HttpServletResponse response,
      HttpStatus status, String message) throws IOException {

    response.setStatus(status.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    ErrorResponse errorResponse = ErrorResponse.builder()
        .message(message)
        .status(status)
        .timestamp(LocalDateTime.now())
        .build();

    response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}