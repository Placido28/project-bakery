package com.placidotech.pasteleria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.User;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);

    // Buscar un usuario por email
    Optional<User> findByEmail(String email);

    // Buscar usuarios por nombre (firstName o lastName)
    List<User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    // Buscar usuarios por rol
    List<User> findByRole(String role);

    // Buscar usuarios no eliminados (soft delete)
    List<User> findByRemovedFalse();
}
