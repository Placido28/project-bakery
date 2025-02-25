package com.placidotech.pasteleria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.placidotech.pasteleria.model.Address;

/**
 *
 * @author PLACIDO
 */
public interface AddressRepository extends JpaRepository<Address, Long>{

    // Método para buscar direcciones por userId
    List<Address> findByUserId(Long userId);
    Optional<Address> findByUserIdAndDefaultAddressTrue(Long userId);
    int countByUserId(Long id);
}
