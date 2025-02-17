package com.placidotech.pasteleria.exception;

public class InvalidAddressTypeException extends IllegalArgumentException{

    public InvalidAddressTypeException(String message){
        super(message);
    }
}
