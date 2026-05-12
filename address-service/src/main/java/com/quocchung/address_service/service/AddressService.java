package com.quocchung.address_service.service;

import com.quocchung.address_service.model.dto.AddressDTO;
import com.quocchung.address_service.model.dto.AddressRequest;
import java.util.List;

public interface AddressService {
   List<AddressDTO> saveAddress(AddressRequest addressRequest);

   List<AddressDTO> updateAddress (AddressRequest addressRequest);

   AddressDTO getAddress(Long id);

   void deteteAddress(Long id);

   List<AddressDTO> getAllAddress();

   List<AddressDTO> getAddressEmployeeId(Long employeeId);
}
