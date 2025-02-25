/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.placidotech.pasteleria.dto;

import lombok.Data;

/**
 *
 * @author PLACIDO
 */
@Data
public class AddressDTO {

    private Long id;
    private String street;
    private String numbers;
    private String lot;
    private String block;
    private String references;
    private String addressType;
    private Long userId;
    private boolean defaultAddress;
}
