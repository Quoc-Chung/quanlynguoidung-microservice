package com.quocchung.address_service.model.dto;

import com.quocchung.address_service.model.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {
  private Long id;

  private String street;

  private Long pinCode;

  private String city;

  private String country;

  private AddressType addressType;
}
