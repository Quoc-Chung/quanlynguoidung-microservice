package com.quocchung.address_service.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
  private Long id;

  private String employeeName;

  private String employeeEmail;

  private String employeeCode;

  private String companyName;
}
