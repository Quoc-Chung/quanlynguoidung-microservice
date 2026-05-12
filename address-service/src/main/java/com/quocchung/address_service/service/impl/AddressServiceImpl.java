package com.quocchung.address_service.service.impl;

import com.quocchung.address_service.Exception.ResourceNotFoundException;
import com.quocchung.address_service.client.EmployeeClient;
import com.quocchung.address_service.model.dto.AddressDTO;
import com.quocchung.address_service.model.dto.AddressRequest;
import com.quocchung.address_service.model.dto.AddressRequestDTO;
import com.quocchung.address_service.model.dto.EmployeeDTO;
import com.quocchung.address_service.model.entity.Address;
import com.quocchung.address_service.repository.AddressRepository;
import com.quocchung.address_service.service.AddressService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;
  private final ModelMapper modelMapper;
  private final EmployeeClient employeeClient;

  @Override
  @Transactional
  public List<AddressDTO> saveAddress(AddressRequest addressRequest) {

    List<Address> listToSave = new ArrayList<>();

    EmployeeDTO employee = employeeClient.getEmployee(addressRequest.getEmployeeId());
    if(employee == null){
      throw new ResourceNotFoundException("User not found with id : "+ addressRequest.getEmployeeId()) ;
    }

    System.out.println("Thong tin nhan vien : "+ employee.getEmployeeName());

    for (AddressRequestDTO dto : addressRequest.getAddressRequestDTOList()) {

      Address address;

      // UPDATE
      if (dto.getId() != null) {

        address = addressRepository.findById(dto.getId())
            .orElseThrow(() ->
                new ResourceNotFoundException("Address not found with id: " + dto.getId()));
      }

      // CREATE
      else {
        address = new Address();
      }

      address.setEmployeeId(employee.getId());
      address.setStreet(dto.getStreet());
      address.setPinCode(dto.getPinCode());
      address.setCity(dto.getCity());
      address.setCountry(dto.getCountry());
      address.setAddressType(dto.getAddressType());

      listToSave.add(address);
    }

    List<Address> saveds = addressRepository.saveAll(listToSave);

    return saveds.stream()
        .map(address -> modelMapper.map(address, AddressDTO.class))
        .toList();
  }

  @Override
  @Transactional
  public List<AddressDTO> updateAddress(AddressRequest addressRequest) {

    List<Address> updatedAddresses = new ArrayList<>();

    EmployeeDTO employee = employeeClient.getEmployee(addressRequest.getEmployeeId());
    if(employee == null){
      throw new ResourceNotFoundException("User not found with id : "+ addressRequest.getEmployeeId()) ;
    }


    for (AddressRequestDTO dto : addressRequest.getAddressRequestDTOList()) {

      Address address = addressRepository.findById(dto.getId())
          .orElseThrow(() ->
              new ResourceNotFoundException(
                  "Address not found with id: " + dto.getId()));

      address.setEmployeeId(employee.getId());
      address.setStreet(dto.getStreet());
      address.setPinCode(dto.getPinCode());
      address.setCity(dto.getCity());
      address.setCountry(dto.getCountry());
      address.setAddressType(dto.getAddressType());

      updatedAddresses.add(address);
    }

    List<Address> savedAddresses = addressRepository.saveAll(updatedAddresses);

    return savedAddresses.stream()
        .map(address -> modelMapper.map(address, AddressDTO.class))
        .toList();
  }
  @Override
  public AddressDTO getAddress(Long id) {

    Address address = addressRepository.findById(id)
        .orElseThrow(() ->
            new ResourceNotFoundException("Address not found with id: " + id));

    return modelMapper.map(address, AddressDTO.class);
  }

  @Override
  @Transactional
  public void deteteAddress(Long id) {

    Address address = addressRepository.findById(id)
        .orElseThrow(() ->
            new ResourceNotFoundException("Address not found with id: " + id));

    addressRepository.delete(address);
  }

  @Override
  public List<AddressDTO> getAllAddress() {

    List<Address> addresses = addressRepository.findAll();

    return addresses.stream()
        .map(address -> modelMapper.map(address, AddressDTO.class))
        .toList();
  }

  @Override
  public List<AddressDTO> getAddressEmployeeId(Long employeeId) {

    List<Address> addresses = addressRepository.findAddressByEmployeeId(employeeId);

    return addresses.stream()
        .map(address -> modelMapper.map(address, AddressDTO.class))
        .toList();
  }
}