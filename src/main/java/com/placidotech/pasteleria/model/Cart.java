package com.placidotech.pasteleria.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Agrega un CartItem a la lista de artículos del carrito y asocia el carrito con el artículo.
     * También actualiza el monto total del carrito después de agregar el artículo.
     *
     * @param item El CartItem que se agregará al carrito.
     */

    public void addItem(CartItem item){
        if (item != null && !this.items.contains(item)) {
            this.items.add(item);
            item.setCart(this);
            updateTotalAmount();  
        }
    }

    /**
     * Elimina un CartItem de la lista de artículos del carrito y desasocia el carrito del artículo.
     * También actualiza el importe total del carrito después de retirar el artículo.
     *
     * @param item El CartItem que se eliminará del carrito.
     */

    public void removeItem(CartItem item){
        if (item != null && this.items.contains(item)) {
            this.items.remove(item);
            item.setCart(null);
            updateTotalAmount();     
        }
    }

    
    /**
     * Calcula el monto total del carrito sumando el producto del precio unitario y la cantidad de cada artículo del carrito.
     * Establece el monto total del carrito en el valor calculado.
     */

    private void updateTotalAmount() {
        this.totalAmount = items.stream()
            .map(item -> item.getUnitPrice() != null 
                ? item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())) 
                : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
