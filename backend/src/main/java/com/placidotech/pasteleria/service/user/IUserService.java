package com.placidotech.pasteleria.service.user;

import java.util.List;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.request.AdminUpdateUserRequest;
import com.placidotech.pasteleria.request.user.CreateUserRequest;
import com.placidotech.pasteleria.request.user.UpdateUserRequest;

public interface IUserService {

    // Obtener todos los usuarios
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO createUserByAdmin(CreateUserRequest request); // Solo para admin
    UserDTO updateUser(Long userId, UpdateUserRequest request);
    UserDTO updateUserAsAdmin(Long userId, AdminUpdateUserRequest request);
    void deleteUser(Long userId);
    List<UserDTO> searchUsersByName(String name);
    List<UserDTO> getUsersByRole(String role);
    void requestEmailChange(Long userId, String newEmail);
    void confirmEmailChange(Long userId, String code);
}
