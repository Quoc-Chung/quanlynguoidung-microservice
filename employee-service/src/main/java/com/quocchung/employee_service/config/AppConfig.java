package com.quocchung.employee_service.config;

import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
@Configuration
public class AppConfig {
  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    /* Cho phép truy cập truc tiếp vào field private */
    modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE)
        /*- Bật mapping theo fieldName -*/
        .setFieldMatchingEnabled(true);
    return modelMapper;
  }

}
