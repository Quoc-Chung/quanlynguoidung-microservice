package com.quocchung.employee_service.repository;

import com.quocchung.employee_service.model.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Query("""
    SELECT e FROM Employee e
    WHERE e.companyName LIKE :companyName
    OR e.employeeCode LIKE :employeeCode
""")
  Optional<Employee> findEmployeeByCompanyNameAndEmployeeCode(
      @Param("companyName") String companyName,
      @Param("employeeCode") String employeeCode
  );
}
