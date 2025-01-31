package com.placidotech.pasteleria.service.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.exception.AlreadyExistsException;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.mapper.UserMapper;
import com.placidotech.pasteleria.model.User;
import com.placidotech.pasteleria.repository.UserRepository;
import com.placidotech.pasteleria.request.CreateUserRequest;
import com.placidotech.pasteleria.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author CristopherPlacidoOca
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findByRemovedFalse().stream()
        .map(UserMapper::toDTO)
        .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AlreadyExistsException("User alreadt exists");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        User updateUser = userRepository.save(user);
        return UserMapper.toDTO(updateUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRemoved(true);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> searchUsersByName(String name) {
        return userRepository.findByFirstNameContainingOrLastNameContaining(name, name).stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByRole(String role) {
        return userRepository.findByRole(role).stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

}
