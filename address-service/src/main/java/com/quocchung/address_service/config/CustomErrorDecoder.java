package com.quocchung.address_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quocchung.address_service.Exception.CustomException;
import com.quocchung.address_service.Exception.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;

/**
 * implement ErrorDecoder của OpenFeign.
 * Nó sẽ chạy khi:
 *     Feign call sang service khác bị lỗi HTTP response (4xx, 5xx).
 * Nó gọi :
 *     decode(String s, Response response)
 */
public class CustomErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String s, Response response) {
    ObjectMapper objectMapper = new ObjectMapper();  // Tạo bộ convert JSON
    objectMapper.findAndRegisterModules(); // Tự động bật hỗ trợ LocalDate, Optional, Java 8 time

    try(InputStream is  = response.body().asInputStream()){
      // Đọc dữ liệu JSON và chuyển thành object Java.
      ErrorResponse errorResponse = objectMapper.readValue(is, ErrorResponse.class);

      return new CustomException(errorResponse.getMessage(),errorResponse.getStatus());
    } catch (IOException e) {
      throw new CustomException(e.getMessage());
    }
  }
}
