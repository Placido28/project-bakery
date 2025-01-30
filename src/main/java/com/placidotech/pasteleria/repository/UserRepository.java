package com.placidotech.pasteleria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.User;

/**
 *
 * @author CristopherPlacidoOca
 */
public interface UserRepository extends JpaRepository<User, Long>{
    boolean existsByEmail(String email);
}
