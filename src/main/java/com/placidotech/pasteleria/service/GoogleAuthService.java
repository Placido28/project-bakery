package com.placidotech.pasteleria.service;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.placidotech.pasteleria.dto.GoogleUser;

@Service
public class GoogleAuthService {
    public GoogleUser getUserInfo(String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(), new JacksonFactory())
            .setAudience(Collections.singletonList("TU_CLIENT_ID")) //Asegura que el token es para la app
            .build();
        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken != null) {
                GoogleIdToken.Payload payload = googleIdToken.getPayload();
                
                return new GoogleUser(
                    payload.getSubject(),
                    (String) payload.get("email"),
                    (String) payload.get("given_name"),
                    (String) payload.get("family_name")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Error validating Google token", e);
        }

        throw new RuntimeException("Invalid Token ID");
    }

}
