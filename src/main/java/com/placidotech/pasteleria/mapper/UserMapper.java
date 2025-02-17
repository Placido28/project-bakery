package com.placidotech.pasteleria.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.model.Address;
import com.placidotech.pasteleria.model.User;

@Component
public class UserMapper {

    @Autowired
    private AddressMapper addressMapper;

    public UserDTO toDTO(User user){
        if (user == null) {
            return null;
        }

        return new UserDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRole(),
            user.getProvider(),
            user.isStateUser(),
            user.isRemoved(),
            user.getAddresses() != null ? 
                user.getAddresses().stream()
                    .map(addressMapper::toDTO)
                    .collect(Collectors.toList()) 
                : new ArrayList<>(),
            user.getDefaultAddress() != null ? addressMapper.toDTO(user.getDefaultAddress()) : null
        );
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber()); // Corregido el nombre del getter
        user.setRole(userDTO.getRole());
        user.setProvider(userDTO.getProvider());
        user.setStateUser(userDTO.isStateUser()); // stateUser -> isActive en entidad
        user.setRemoved(userDTO.isRemoved());

        if (userDTO.getAddresses() != null) {
            List<Address> addresses = userDTO.getAddresses().stream()
                .map(addressMapper::toEntity)
                .collect(Collectors.toList());
            user.setAddresses(addresses);
        }

        if (userDTO.getDefaultAddress() != null) {
            user.setDefaultAddress(addressMapper.toEntity(userDTO.getDefaultAddress()));
        }

        return user;
    }
}
