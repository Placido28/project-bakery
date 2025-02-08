package com.placidotech.pasteleria.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    public void setTotalPrice(){
        if (this.unitPrice != null && this.quantity > 0) {
            this.totalPrice = this.unitPrice.multiply(new BigDecimal(this.quantity));
        }else{
            this.totalPrice = BigDecimal.ZERO;
        }
    }

    public void setQuantity(int quantity){
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity cannot be negative");
        }
        this.quantity = quantity;
        setTotalPrice();
    }

    public void setUnitPrice(BigDecimal unitPrice){
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The unit price cannot be null or negative");
        }
        this.unitPrice = unitPrice;
        setTotalPrice();
    }
}
