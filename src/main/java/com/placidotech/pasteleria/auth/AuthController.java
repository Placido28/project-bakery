package com.placidotech.pasteleria.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.placidotech.pasteleria.dto.UserDTO;
import com.placidotech.pasteleria.request.EmailRequest;
import com.placidotech.pasteleria.request.user.GoogleLoginRequest;
import com.placidotech.pasteleria.request.user.RegisterUserRequest;
import com.placidotech.pasteleria.request.user.ResetPasswordRequest;
import com.placidotech.pasteleria.response.ApiResponse;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegisterUserRequest request) {
        UserDTO userDTO = authService.registerUser(request);
        return ResponseEntity.status(CREATED).body(userDTO);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token) {
        authService.activateAccount(token);
        return ResponseEntity.ok("Account activated successfully");
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendPasswordReset(@RequestBody EmailRequest request) {
        authService.sendPasswordReset(request.getEmail());
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getResetToken(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        authService.logout(request.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/google/login")
    public ResponseEntity<AuthResponse> loginWithGoogle(@RequestBody GoogleLoginRequest request) {
        AuthResponse response = authService.loginWithGoogle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/google/register")
    public ResponseEntity<AuthResponse> registerWithGoogle(@RequestBody GoogleLoginRequest request) {
        AuthResponse response = authService.registerWithGoogle(request);
        return ResponseEntity.ok(response);
    }
    
}
