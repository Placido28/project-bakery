package com.placidotech.pasteleria.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.placidotech.pasteleria.dto.OrderDTO;
import com.placidotech.pasteleria.dto.OrderItemDTO;
import com.placidotech.pasteleria.model.Order;
import com.placidotech.pasteleria.model.OrderItem;

/**
 *
 * @author CristopherPlacidoOca
 */

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    
    // Mapeo de Order a OrderDTO
    @Mapping(target = "items", source = "items")
    @Mapping(target = "orderStatus", source = "orderStatus") // Mapea el estado del pedido
    OrderDTO toDTO(Order order);

    // Mapeo de OrderDTO a order
    @Mapping(target = "items", source = "items")
    @Mapping(target = "orderStatus", source = "orderStatus") //Mapea el estado del pedido
    Order toEntity(OrderDTO orderDTO);

    // Mapeo de OrderItem a OrderItemDTO
    @Mapping(target = "productId", source = "product.id")
    OrderItemDTO toDTO(OrderItem orderItem);

    // Mapeo de OrderItemDTO a OrderItem
    @Mapping(target = "product", ignore = true)
    OrderItem toEntity(OrderItemDTO orderItemDTO);
}
