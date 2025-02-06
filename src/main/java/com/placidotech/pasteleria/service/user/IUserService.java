package com.placidotech.pasteleria.service.user;

import java.util.List;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.request.CreateUserRequest;
import com.placidotech.pasteleria.request.UserUpdateRequest;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface IUserService {

    // Obtener todos los usuarios
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO createUser(CreateUserRequest request);
    UserDTO updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);
    List<UserDTO> searchUsersByName(String name);
    List<UserDTO> getUsersByRole(String role);

}
