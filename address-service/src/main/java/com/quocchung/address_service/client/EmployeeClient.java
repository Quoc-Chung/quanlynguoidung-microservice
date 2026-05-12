package com.quocchung.address_service.client;

import com.quocchung.address_service.config.FeignConfig;
import com.quocchung.address_service.model.dto.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "employee-service",
    url = "${app.feign.user-service:http://localhost:8081}",
    configuration = FeignConfig.class,
    path = "/api/employees"
)
public interface EmployeeClient {
  @GetMapping("/{id}")
  EmployeeDTO getEmployee(@PathVariable Long id);
}
