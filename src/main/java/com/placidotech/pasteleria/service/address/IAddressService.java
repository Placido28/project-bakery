/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.placidotech.pasteleria.service.address;

import java.util.List;

import com.placidotech.pasteleria.dto.AddressDTO;
import com.placidotech.pasteleria.request.address.CreateAddressRequest;
import com.placidotech.pasteleria.request.address.UpdateAddressRequest;

/**
 *
 * @author PLACIDO
 */
public interface IAddressService {
    AddressDTO getAddressById(Long id);
    AddressDTO createAddress(CreateAddressRequest request);
    AddressDTO updateAddress(Long id, UpdateAddressRequest request);
    void deleteAddress(Long id);
    List<AddressDTO> getAddressesByUserId(Long userId);
    void setDefaultAddress(Long addressId, Long userId);
}
