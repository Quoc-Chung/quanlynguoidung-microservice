package com.quocchung.employee_service.client.fallback;


import com.quocchung.employee_service.Exception.CustomException;
import com.quocchung.employee_service.client.AddressClient;
import com.quocchung.employee_service.model.dto.AddressDTO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AddressClientFallback implements AddressClient {
  @Override
  public List<AddressDTO> getAddressByEmployeeId(Long employeeId) {
    // Trả list rỗng khi Address Service không họt động
    // return Collections.emptyList();
    // Hoặc throw exception tùy bạn
    throw new CustomException("Address Service không khả dụng" );
  }
}
