package com.placidotech.pasteleria.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.placidotech.pasteleria.response.ApiResponse;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 *
 * @author CristopherPlacidoOca
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExistsException(AlreadyExistsException ex){
        return ResponseEntity.status(CONFLICT).body(new ApiResponse(ex.getMessage(), null));
    }
}
