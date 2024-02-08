package dev.awn.orderservice.core.order.service;

import dev.awn.orderservice.core.order.dto.OrderDTO;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

}
