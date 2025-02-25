package com.placidotech.pasteleria.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String provider; //"LOCAL" o "GOOGLE"
    private boolean stateUser; //Indica si la cuenta está activa
    private boolean removed; // Indica si el usuario fue eliminado lógicamente
    private List<AddressDTO> addresses; // Lista de direcciones del usuario
    private AddressDTO defaultAddress; // Dirección predeterminada
}