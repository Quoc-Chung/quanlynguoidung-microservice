package com.quocchung.employee_service.client;

import com.quocchung.employee_service.client.fallback.AddressClientFallback;
import com.quocchung.employee_service.config.FeignConfig;
import com.quocchung.employee_service.model.dto.AddressDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
    name = "address-service",
    url = "${app.feign.address-service:http://localhost:8082}",
    path = "/api/address",
    configuration = FeignConfig.class,
    fallback = AddressClientFallback.class
)
public interface AddressClient {

  @GetMapping("/employee/{employeeId}")
  List<AddressDTO> getAddressByEmployeeId(
      @PathVariable Long employeeId
  );
}
