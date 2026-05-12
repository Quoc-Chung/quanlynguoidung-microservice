package com.quocchung.employee_service.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
  private Long id;

  private String employeeName;

  private String employeeEmail;

  private String employeeCode;

  private String companyName;

  private List<AddressDTO> address;
}
