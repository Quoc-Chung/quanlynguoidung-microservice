package com.quocchung.address_service.controller;

import com.quocchung.address_service.model.dto.AddressDTO;
import com.quocchung.address_service.model.dto.AddressRequest;
import com.quocchung.address_service.service.AddressService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
  private final AddressService addressService;

  @PostMapping
  public List<AddressDTO> saveAddress(@RequestBody AddressRequest request) {
    return addressService.saveAddress(request);
  }


  @PutMapping("/{id}")
  public List<AddressDTO> updateAddress(
      @RequestBody AddressRequest request
  ) {
    return addressService.updateAddress( request);
  }


  @GetMapping("/{id}")
  public AddressDTO getAddress(@PathVariable Long id) {
    return addressService.getAddress(id);
  }


  @DeleteMapping("/{id}")
  public String deleteAddress(@PathVariable Long id) {

    addressService.deteteAddress(id);

    return "Delete success";
  }


  @GetMapping("/employee/{employeeId}")
  public List<AddressDTO> getAddressByEmployeeId(
      @PathVariable Long employeeId
  ) {
    return addressService.getAddressEmployeeId(employeeId);
  }

  @GetMapping("/all")
  public List<AddressDTO> getAllAddress(
  ) {
    return addressService.getAllAddress();
  }
}
