package com.quocchung.address_service.client.fallback;


import com.quocchung.address_service.client.EmployeeClient;
import com.quocchung.address_service.model.dto.EmployeeDTO;
import com.quocchung.common_lib.Exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class EmployeeClientFallback implements EmployeeClient{

  @Override
  public EmployeeDTO getEmployee(Long id) {
    throw new BadRequestException("EmployeeService not active. Please try again later");
  }
}
