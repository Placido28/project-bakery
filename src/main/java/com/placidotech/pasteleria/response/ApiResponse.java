package com.placidotech.pasteleria.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author CristopherPlacidoOca
 */

@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;
}
