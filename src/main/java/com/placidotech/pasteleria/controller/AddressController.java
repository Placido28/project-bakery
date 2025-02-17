package com.placidotech.pasteleria.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.AddressDTO;
import com.placidotech.pasteleria.request.address.CreateAddressRequest;
import com.placidotech.pasteleria.request.address.UpdateAddressRequest;
import com.placidotech.pasteleria.service.address.IAddressService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author PLACIDO
 */
@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        AddressDTO addressDTO = addressService.getAddressById(id);
        return ResponseEntity.ok(addressDTO);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressDTO>> getAddressesByUserId(@PathVariable Long userId){
        List<AddressDTO> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/add")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody CreateAddressRequest request) {
        AddressDTO createdAddress = addressService.createAddress(request);
        return ResponseEntity.ok(createdAddress);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody UpdateAddressRequest request) {
        AddressDTO updatedAddress = addressService.updateAddress(id, request);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/set-default/{addressId}")
    public ResponseEntity<Void> setDefaultAddress(@PathVariable Long addressId, @RequestParam Long userId) {
        addressService.setDefaultAddress(addressId, userId);
        return ResponseEntity.noContent().build();
    }
}
