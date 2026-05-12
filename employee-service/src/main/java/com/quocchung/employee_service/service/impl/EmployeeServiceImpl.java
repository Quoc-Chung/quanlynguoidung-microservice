package com.quocchung.employee_service.service.impl;

import com.quocchung.employee_service.Exception.BadRequestException;
import com.quocchung.employee_service.Exception.ResourceNotFoundException;
import com.quocchung.employee_service.client.AddressClient;
import com.quocchung.employee_service.model.dto.EmployeeDTO;
import com.quocchung.employee_service.model.entity.Employee;
import com.quocchung.employee_service.repository.EmployeeRepository;
import com.quocchung.employee_service.service.EmployeeService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final ModelMapper modelMapper;
  private  final AddressClient addressClient;

  @Override
  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    Employee employee = modelMapper.map(employeeDTO, Employee.class);
    Employee savedEmployee = employeeRepository.save(employee);
    return modelMapper.map(savedEmployee, EmployeeDTO.class);
  }

  @Override
  public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
    if(id == null || employeeDTO.getId() == null){
      throw new BadRequestException("Please provide employee id");
    }
    if(!Objects.equals(id, employeeDTO.getId())){
      throw new BadRequestException("Id mismatch");
    }
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

    employee.setEmployeeName(employeeDTO.getEmployeeName());
    employee.setEmployeeEmail(employeeDTO.getEmployeeEmail());
    employee.setEmployeeCode(employeeDTO.getEmployeeCode());
    employee.setCompanyName(employeeDTO.getCompanyName());

    Employee updatedEmployee = employeeRepository.save(employee);

    return modelMapper.map(updatedEmployee, EmployeeDTO.class);
  }

  @Override
  public void deleteEmployee(Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found delete with id : "+ id));
    employeeRepository.deleteById(id);
  }

  @Override
  public EmployeeDTO getEmployee(Long id) {

    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : "+ id));

    EmployeeDTO  employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
    employeeDTO.setAddress(addressClient.getAddressByEmployeeId(employeeDTO.getId()));

    return employeeDTO;
  }

  @Override
  public List<EmployeeDTO> getAllEmployees() {
    return employeeRepository.findAll()
        .stream()
        .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public EmployeeDTO getEmployeeByEmployeeCodeAndCompanyName(String employeeCode,
      String companyName) {
    Employee employee = employeeRepository.findEmployeeByCompanyNameAndEmployeeCode(companyName, employeeCode).orElseThrow(() ->
        new ResourceNotFoundException("Employee Not Found ")
    );

    return modelMapper.map(employee,EmployeeDTO.class);
  }
}
