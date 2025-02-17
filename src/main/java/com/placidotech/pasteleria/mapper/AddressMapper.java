/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.placidotech.pasteleria.mapper;

import org.springframework.stereotype.Component;

import com.placidotech.pasteleria.dto.AddressDTO;
import com.placidotech.pasteleria.enums.AddressType;
import com.placidotech.pasteleria.exception.InvalidAddressTypeException;
import com.placidotech.pasteleria.model.Address;
import com.placidotech.pasteleria.request.address.CreateAddressRequest;
import com.placidotech.pasteleria.request.address.UpdateAddressRequest;

/**
 *
 * @author PLACIDO
 */
@Component
public class AddressMapper {

    // Método para convertir de Address a AddressDTO
    public AddressDTO toDTO(Address address) {
        if (address == null) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setNumbers(address.getNumbers());
        addressDTO.setLot(address.getLot());
        addressDTO.setBlock(address.getBlock());
        addressDTO.setReferences(address.getReferences());
        addressDTO.setAddressType(address.getAddressType() != null ? address.getAddressType().name() : null);
        addressDTO.setUserId(address.getUser() != null ? address.getUser().getId() : null);
        addressDTO.setDefaultAddress(address.isDefaultAddress());

        return addressDTO;
    }

    // Método para convertir de AddressDTO a Address
    public Address toEntity(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }

        Address address = new Address();
        address.setId(addressDTO.getId());
        address.setStreet(addressDTO.getStreet());
        address.setNumbers(addressDTO.getNumbers());
        address.setLot(addressDTO.getLot());
        address.setBlock(addressDTO.getBlock());
        address.setReferences(addressDTO.getReferences());
        address.setAddressType(addressDTO.getAddressType() != null ? AddressType.valueOf(addressDTO.getAddressType()) : null);
        address.setDefaultAddress(addressDTO.isDefaultAddress());

        return address;
    }

    public AddressDTO toDTO(CreateAddressRequest request) {
        if (request == null) {
            return null;
        }
    
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(request.getStreet());
        addressDTO.setNumbers(request.getNumbers());
        addressDTO.setLot(request.getLot());
        addressDTO.setBlock(request.getBlock());
        addressDTO.setReferences(request.getReferences());
        addressDTO.setAddressType(request.getAddressType() != null ? request.getAddressType() : null);
        addressDTO.setUserId(request.getUserId());
        addressDTO.setDefaultAddress(request.isDefaultAddress());
    
        return addressDTO;
    }
    
    public AddressDTO toDTO(UpdateAddressRequest request) {
        if (request == null) {
            return null;
        }
    
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(request.getStreet());
        addressDTO.setNumbers(request.getNumbers());
        addressDTO.setLot(request.getLot());
        addressDTO.setBlock(request.getBlock());
        addressDTO.setReferences(request.getReferences());
        addressDTO.setAddressType(request.getAddressType() != null ? request.getAddressType() : null);
        addressDTO.setDefaultAddress(request.isDefaultAddress());
    
        return addressDTO;
    }
}
