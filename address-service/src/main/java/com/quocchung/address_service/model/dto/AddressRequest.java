package com.quocchung.address_service.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
   private Long  employeeId;
   private List<AddressRequestDTO> addressRequestDTOList;
}
