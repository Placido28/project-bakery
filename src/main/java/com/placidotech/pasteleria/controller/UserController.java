package com.placidotech.pasteleria.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.exception.AlreadyExistsException;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.request.CreateUserRequest;
import com.placidotech.pasteleria.request.UserUpdateRequest;
import com.placidotech.pasteleria.response.ApiResponse;
import com.placidotech.pasteleria.service.user.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



/**
 *
 * @author CristopherPlacidoOca
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse("Success", users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("Success", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try{
            UserDTO userDTO = userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse("User created successfully", userDTO));
        } catch(AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        try {
            UserDTO userDTO = userService.updateUser(id, request);
            return ResponseEntity.ok(new ApiResponse("User updated successfully", userDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser (@PathVariable Long id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully", null));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
