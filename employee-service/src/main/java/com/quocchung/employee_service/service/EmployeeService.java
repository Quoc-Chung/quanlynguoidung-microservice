package com.quocchung.employee_service.service;

import com.quocchung.employee_service.model.dto.EmployeeDTO;
import java.util.List;

public interface EmployeeService {

    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

    EmployeeDTO getEmployee(Long id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeByEmployeeCodeAndCompanyName(String employeeCode, String companyName);
}