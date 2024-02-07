package dev.awn.orderservice.core.order.service;

import dev.awn.orderservice.core.order.dto.OrderDTO;

public interface OrderService {

    public OrderDTO createOrder(OrderDTO orderDTO);

}
