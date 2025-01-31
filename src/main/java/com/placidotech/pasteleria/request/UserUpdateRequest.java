/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.placidotech.pasteleria.request;

import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
