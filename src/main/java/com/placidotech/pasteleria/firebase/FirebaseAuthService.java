package com.placidotech.pasteleria.firebase;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Service
public class FirebaseAuthService {

    public FirebaseUser getUserInfo(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

            String fullName = decodedToken.getName();
            String[] nameParts = fullName != null ? fullName.split(" ", 2) : new String[]{"", ""};

            return new FirebaseUser(
                decodedToken.getUid(),
                nameParts[0],
                nameParts.length > 1 ? nameParts[1] : "",
                decodedToken.getEmail()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error validating Firebase token", e);
        }
    }
}
