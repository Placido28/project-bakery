package com.placidotech.pasteleria.exception;

/**
 *
 * @author CristopherPlacidoOca
 */
public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
