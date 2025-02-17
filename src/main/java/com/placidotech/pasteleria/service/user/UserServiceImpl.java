package com.placidotech.pasteleria.service.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.exception.ResourceNotFoundException;
import com.placidotech.pasteleria.mapper.UserMapper;
import com.placidotech.pasteleria.model.Address;
import com.placidotech.pasteleria.model.User;
import com.placidotech.pasteleria.repository.AddressRepository;
import com.placidotech.pasteleria.repository.UserRepository;
import com.placidotech.pasteleria.request.AdminUpdateUserRequest;
import com.placidotech.pasteleria.request.user.CreateUserRequest;
import com.placidotech.pasteleria.request.user.UpdateUserRequest;
import com.placidotech.pasteleria.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final AddressRepository addressRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findByRemovedFalse().stream()
        .map(userMapper::toDTO)
        .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByRole(String role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUserByAdmin(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("The email is already in use.");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");
        user.setProvider("LOCAL");
        user.setRemoved(false);
        user.setStateUser(false); // No puede iniciar sesión hasta establecer su contraseña

        //Generar Token de activación
        String activationToken = UUID.randomUUID().toString();
        user.setActivationToken(activationToken);

        //Guardar usuario sin contraseña
        user = userRepository.save(user);

        //Enviar correo con enlace de activación
        emailService.sendActivationEmail(user.getEmail(), activationToken);

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Buscar la dirección por su ID
        Address defaultAddress = addressRepository.findById(request.getDefaultAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        //Establecer la dirección al usuario
        user.setDefaultAddress(defaultAddress);
        
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUserAsAdmin(Long userId, AdminUpdateUserRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("The email is already in use.");
            }
            user.setEmail(request.getEmail());
        }

        user.setRole(request.getRole());

        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    // Servicio para cambio de email con verificación
    @Override
    public void requestEmailChange(Long userId, String newEmail) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("The email is already in use.");
        }

        String verificationCode = generateVerificationCode();
        emailService.sendVerificationEmail(newEmail, verificationCode);

        user.setPendingEmail(newEmail);
        user.setEmailVerificationCode(verificationCode);
        userRepository.save(user);
    }

    @Override
    public void confirmEmailChange(Long userId, String code) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!code.equals(user.getEmailVerificationCode())) {
            throw new IllegalArgumentException("Incorrect verification code");
        }

        user.setEmail(user.getPendingEmail());
        user.setPendingEmail(null);
        user.setEmailVerificationCode(null);
        userRepository.save(user);
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase(); // Ejemplo: "A1B2C3"
    }

}
