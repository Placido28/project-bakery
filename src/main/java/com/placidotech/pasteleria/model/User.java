package com.placidotech.pasteleria.model;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToOne
    private Address defaultAddress;

    private String phoneNumber;
    
    @Column(unique = true)
    private String pendingEmail; // Nuevo email pendiente de confirmación

    private String emailVerificationCode; // Código de verificación

    @Column(nullable = false)
    private String role; // "ROLE_USER" o "ROLE_ADMIN"

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    // Soft Delete
    @Column(nullable = false)
    private boolean removed = false;

    @Column
    private String provider; //"LOCAL" o "GOOGLE"

    @Column(unique = true)
    private String googleId; //ID de Google si usa Google Sign-In

    @Column
    private String activationToken; // Token de activación enviado por correo

    @Column(unique = true)
    private String resetToken;

    @Column(nullable = false)
    private boolean stateUser = false;


    /**
     * Asocia un carrito al usuario y asegura la consistencia de la relación bidireccional.
     *
     * @param cart El carrito que se asociará al usuario.
     */
    public void setCart(Cart cart) {
        if (cart == null) {
            if (this.cart != null) {
                this.cart.setUser(null);
            }
        } else {
            cart.setUser(this);
        }
        this.cart = cart;
    }

    public void encryptPassword(){
        if (this.password != null && !this.password.isEmpty()) {
            this.password = new BCryptPasswordEncoder().encode(this.password);
        }
    }
}
