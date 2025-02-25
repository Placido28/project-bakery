package com.placidotech.pasteleria.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseUser {
    private String uid;
    private String firstName;
    private String lastName;
    private String email;
}
