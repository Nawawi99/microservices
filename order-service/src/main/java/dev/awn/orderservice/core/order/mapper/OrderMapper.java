package dev.awn.orderservice.core.order.mapper;

import dev.awn.orderservice.core.order.dto.ItemDTO;
import dev.awn.orderservice.core.order.dto.OrderDTO;
import dev.awn.orderservice.core.order.model.Item;
import dev.awn.orderservice.core.order.model.Order;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public List<Order> toModels(List<OrderDTO> orderDTOs) {
        return orderDTOs.stream()
                        .map(this::toModel)
                        .collect(Collectors.toList());
    }

    public Order toModel(OrderDTO orderDTO) {
        return Order.builder()
                    .id(orderDTO.getId())
                    .number(orderDTO.getNumber())
                    .items(orderDTO.getItems()
                                   .stream()
                                   .map(this::toModel)
                                   .collect(Collectors.toList()))
                    .build();
    }

    public List<OrderDTO> toDTOs(List<Order> orders) {
        return orders.stream()
                     .map(this::toDTO)
                     .collect(Collectors.toList());
    }

    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                       .id(order.getId())
                       .number(order.getNumber())
                       .items(order.getItems()
                                   .stream()
                                   .map(this::toDTO)
                                   .collect(Collectors.toList()))
                       .build();
    }

    private Item toModel(ItemDTO itemDTO) {
        return Item.builder()
                   .code(itemDTO.getCode())
                   .price(itemDTO.getPrice())
                   .quantity(itemDTO.getQuantity())
                   .build();
    }

    private ItemDTO toDTO(Item item) {
        return ItemDTO.builder()
                      .code(item.getCode())
                      .price(item.getPrice())
                      .quantity(item.getQuantity())
                      .build();
    }

}
