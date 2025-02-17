package com.placidotech.pasteleria.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.exception.AlreadyExistsException;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.request.AdminUpdateUserRequest;
import com.placidotech.pasteleria.request.EmailChangeRequest;
import com.placidotech.pasteleria.request.user.CreateUserRequest;
import com.placidotech.pasteleria.request.user.UpdateUserRequest;
import com.placidotech.pasteleria.response.ApiResponse;
import com.placidotech.pasteleria.service.user.IUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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
            UserDTO userDTO = userService.createUserByAdmin(request);
            return ResponseEntity.ok(new ApiResponse("User created successfully", userDTO));
        } catch(AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        UserDTO updatedUser = userService.updateUser(id, request);
        ApiResponse response = new ApiResponse("Successfully updated user", updatedUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/users/{id}")
    public ResponseEntity<ApiResponse> updateUserAsAdmin(@PathVariable Long id, @RequestBody AdminUpdateUserRequest request) {
        UserDTO updatedUser = userService.updateUserAsAdmin(id, request);
        ApiResponse response = new ApiResponse("User successfully updated by administrator", updatedUser);
        return ResponseEntity.ok(response);
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

    @PostMapping("/users/{id}/change-email")
    public ResponseEntity<ApiResponse> requestEmailChange(@PathVariable Long id, @RequestBody EmailChangeRequest request) {
        userService.requestEmailChange(id, request.getNewEmail());
        ApiResponse response = new ApiResponse("A verification email has been sent.", null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/{id}/confirm-email")
    public ResponseEntity<ApiResponse> confirmEmailChange(@PathVariable Long id, @RequestParam String code) {
        userService.confirmEmailChange(id, code);
        ApiResponse response = new ApiResponse("Email updated correctly.", null);
        return ResponseEntity.ok(response);
    }
}
