package com.quocchung.employee_service.model.entity;

import com.quocchung.common_lib.entity.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Employee extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "employee_name")
  private String employeeName;

  @Column(name = "employee_email")
  private String employeeEmail;

  @Column(name = "employee_code")
  private String employeeCode;

  @Column(name = "company_name")
  private String companyName;
}