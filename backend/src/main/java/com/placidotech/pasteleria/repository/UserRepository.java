package com.placidotech.pasteleria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.placidotech.pasteleria.model.User;

/**
 *
 * @author CristopherPlacidoOca
 */
@Repository
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

    Optional<User> findByActivationToken(String activationToken);

    Optional<User> findByResetToken(String resetToken);
}
