package com.placidotech.pasteleria.exception;


public class AccountNotActivatedException extends RuntimeException{
    public AccountNotActivatedException(String message){
        super(message);
    }
}
