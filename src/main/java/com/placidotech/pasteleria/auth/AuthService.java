package com.placidotech.pasteleria.auth;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.placidotech.pasteleria.dto.GoogleUser;
import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.exception.EmailAlreadyInUseException;
import com.placidotech.pasteleria.exception.EmailNotFoundException;
import com.placidotech.pasteleria.exception.InvalidTokenException;
import com.placidotech.pasteleria.jwt.JwtProvider;
import com.placidotech.pasteleria.mapper.UserMapper;
import com.placidotech.pasteleria.model.RefreshToken;
import com.placidotech.pasteleria.model.User;
import com.placidotech.pasteleria.repository.RefreshTokenRepository;
import com.placidotech.pasteleria.repository.UserRepository;
import com.placidotech.pasteleria.request.user.GoogleLoginRequest;
import com.placidotech.pasteleria.request.user.RegisterUserRequest;
import com.placidotech.pasteleria.response.ApiResponse;
import com.placidotech.pasteleria.service.EmailService;
import com.placidotech.pasteleria.service.GoogleAuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final GoogleAuthService googleAuthService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailService emailService;

    // Autenticación con email y contraseña
    public ResponseEntity<ApiResponse> authenticate(AuthRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!user.isStateUser()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponse("The account is not activated", null));
    }

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    // Generación de los tokens
    String accessToken = jwtProvider.generateAccessToken(user);
    String refreshToken = generateRefreshToken(user);

    return ResponseEntity.ok(new ApiResponse("Login successful", new AuthResponse(accessToken, refreshToken)));
}


    // Registro de usuario con email y contraseña
    public UserDTO registerUser(RegisterUserRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyInUseException("The email is already in use");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole("ROLE_USER");
        user.setProvider("LOCAL");
        user.setStateUser(false); // Requiere activación
        user.setRemoved(false);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Generar token de activación
        String activationToken = UUID.randomUUID().toString();
        user.setActivationToken(activationToken);

        // Guardar usuario
        user = userRepository.save(user);

        // Enviar correo de activación
        emailService.sendActivationEmail(user.getEmail(), activationToken);

        return userMapper.toDTO(user);
    }

    //Activación de cuenta mediante token enviado por correo.
    public void activateAccount(String token) {
        User user = userRepository.findByActivationToken(token)
            .orElseThrow(() -> new InvalidTokenException("Invalid activation token."));

        user.setStateUser(true);
        user.setActivationToken(null);
        userRepository.save(user);
    }

    // Enviar correo para restablecimiento de contraseña.
    public void sendPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new EmailNotFoundException("Email not found."));

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    //Restablecimiento de contraseña mediante token de recuperación.
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
            .orElseThrow(() -> new InvalidTokenException("Invalid reset token."));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
    }


    public String generateRefreshToken(User user){
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtProvider.getRefreshTokenExpiration()));
    
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public AuthResponse refreshToken(RefreshTokenRequest request){
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(request.getRefreshToken());

        if (refreshTokenOpt.isPresent() && refreshTokenOpt.get().getExpiryDate().isAfter(Instant.now())) {
            User user = refreshTokenOpt.get().getUser();
            String newAccessToken = jwtProvider.generateAccessToken(user);
            return new AuthResponse(newAccessToken, request.getRefreshToken());
        }

        throw new RuntimeException("Invalid refresh token");
    }

    public void logout(String refreshToken){
        refreshTokenRepository.findByToken(refreshToken)
            .ifPresent(refreshTokenRepository::delete);
    }

    public AuthResponse loginWithGoogle(GoogleLoginRequest request) {
        // Obtener datos reales desde el idToken
        GoogleUser googleUser = googleAuthService.getUserInfo(request.getIdToken());

        // Buscar al usuario en la base de datos
        Optional<User> existingUser = userRepository.findByEmail(googleUser.getEmail());

        if (!existingUser.isPresent()) {
            throw new RuntimeException("User not registered. Sign up with Google first.");
        }

        // Generar tokens de acceso y retorno
        String accessToken = jwtProvider.generateAccessToken(existingUser.get());
        String refreshToken = jwtProvider.generateRefreshToken(existingUser.get());

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse registerWithGoogle(GoogleLoginRequest request) {
        // Obtener datos reales desde el idToken
        GoogleUser googleUser = googleAuthService.getUserInfo(request.getIdToken());

        User user = userRepository.findByEmail(googleUser.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setFirstName(googleUser.getFirstName());
                    newUser.setLastName(googleUser.getLastName());
                    newUser.setEmail(googleUser.getEmail());
                    newUser.setProvider("GOOGLE");
                    newUser.setGoogleId(googleUser.getGoogleId());
                    newUser.setRole("ROLE_USER");
                    newUser.setStateUser(true); // Google users are automatically activated
                    newUser.setRemoved(false);
                    return userRepository.save(newUser);
                });

        // Generar tokens para el usuario autenticado
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken);
    }
}
