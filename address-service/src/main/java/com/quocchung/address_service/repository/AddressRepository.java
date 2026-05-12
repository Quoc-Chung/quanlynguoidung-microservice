package com.quocchung.address_service.repository;

import com.quocchung.address_service.model.entity.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {


  @Query("SELECT a FROM Address a WHERE a.employeeId = :employeeId")
  List<Address> findAddressByEmployeeId(@Param("employeeId") Long employeeId);

}

