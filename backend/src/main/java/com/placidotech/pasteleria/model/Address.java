package com.placidotech.pasteleria.model;

import java.util.List;

import com.placidotech.pasteleria.enums.AddressType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String numbers;

    private String lot;
    private String block;
    
    @Column(name = "reference_details")
    private String referencesDetails;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private boolean defaultAddress;

    @OneToMany(mappedBy = "shippingAddress")
    private List<Order> orders;
}
