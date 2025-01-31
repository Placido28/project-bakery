package com.placidotech.pasteleria.dto;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
