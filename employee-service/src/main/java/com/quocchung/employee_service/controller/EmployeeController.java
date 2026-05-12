package com.quocchung.employee_service.controller;

import com.quocchung.employee_service.Exception.MissingParameterException;
import com.quocchung.employee_service.model.dto.EmployeeDTO;
import com.quocchung.employee_service.service.EmployeeService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
    return new ResponseEntity<>(employeeService.saveEmployee(employeeDTO), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeDTO> updateEmployee(
      @PathVariable Long id,
      @RequestBody EmployeeDTO employeeDTO) {
    return new ResponseEntity<>(employeeService.updateEmployee(id, employeeDTO), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(
      @PathVariable Long id) {

    employeeService.deleteEmployee(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> getEmployee(
      @PathVariable Long id) {

    EmployeeDTO employee = employeeService.getEmployee(id);

    return ResponseEntity.ok(employee);
  }

  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {

    List<EmployeeDTO> employees =
        employeeService.getAllEmployees();

    return ResponseEntity.ok(employees);
  }

  @GetMapping("/get-employee-employeecode-and-companyname")
  public ResponseEntity<EmployeeDTO> getEmployeeByEmployeeCodeAndCompanyName(
      @RequestParam(required = false) String employeeCode,
      @RequestParam (required = false) String companyName){
      List<String> missingParammeters = new ArrayList<>();
      if(employeeCode == null || employeeCode.trim().isEmpty()){
         missingParammeters.add("empCode");
      }
      if(companyName == null && companyName.trim().isEmpty()){
        missingParammeters.add("companyName");
      }

      if(!missingParammeters.isEmpty()){
        String finalMessage = missingParammeters.stream().collect(Collectors.joining(","));
        throw new MissingParameterException("Please provider : " + finalMessage);
      }
      EmployeeDTO employeeDTO = employeeService.getEmployeeByEmployeeCodeAndCompanyName(employeeCode, companyName);
      return new ResponseEntity<>(employeeDTO, HttpStatus.OK);

  }
}