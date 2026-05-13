package com.quocchung.address_service.model.entity;

import com.quocchung.address_service.model.enums.AddressType;
import com.quocchung.common_lib.entity.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name="address")
public class Address extends AuditableEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long employeeId;

  private String street;

  private Long pinCode;

  private String city;

  private String country;

  /**
   * address.setAddressType(AddressType.HOME);
   * Luu enum xuong db duoi dang chuoi
   */
  @Enumerated(EnumType.STRING)
  private AddressType addressType;

}
