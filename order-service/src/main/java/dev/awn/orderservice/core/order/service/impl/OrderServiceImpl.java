package dev.awn.orderservice.core.order.service.impl;

import com.google.gson.Gson;
import dev.awn.orderservice.common.exception.BadRequestException;
import dev.awn.orderservice.common.exception.PaymentException;
import dev.awn.orderservice.core.order.dto.InventoryResponse;
import dev.awn.orderservice.core.order.dto.OrderDTO;
import dev.awn.orderservice.core.order.event.OrderPlacedEvent;
import dev.awn.orderservice.core.order.mapper.OrderMapper;
import dev.awn.orderservice.core.order.message.OrderPaymentMessage;
import dev.awn.orderservice.core.order.model.Item;
import dev.awn.orderservice.core.order.model.Order;
import dev.awn.orderservice.core.order.repository.OrderRepository;
import dev.awn.orderservice.core.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@RefreshScope
public class OrderServiceImpl implements OrderService {
    private final String PAYMENT_EXCHANGE = "payment-exchange";
    private final String PAYMENT_ROUTING_KEY = "payment-routing-key";

    private final String NOTIFICATION_TOPIC = "notification-topic";

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final Gson gson;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toModel(orderDTO);

        order.setNumber(UUID.randomUUID()
                            .toString());

        List<String> codes = order.getItems()
                                  .stream()
                                  .map(Item::getCode)
                                  .toList();

        InventoryResponse[] inventoryStocks
                = webClientBuilder.build()
                                  .get()
                                  .uri("http://inventory-service/api/v1/inventory",
                                          uriBuilder -> uriBuilder.queryParam("codes", codes)
                                                                  .build())
                                  .retrieve()
                                  .bodyToMono(InventoryResponse[].class)
                                  .block();

        if (inventoryStocks == null) {
            throw new BadRequestException("Item/s not in stock");
        }

        boolean productsInStock = Arrays.stream(inventoryStocks)
                                        .allMatch(InventoryResponse::getInStock);

        if (productsInStock) {
            double totalOrderValue = order.getItems()
                                               .stream()
                                               .mapToDouble(Item::getPrice)
                                               .reduce(0.0, Double::sum);

            OrderPaymentMessage orderPaymentMessage = new OrderPaymentMessage(order.getNumber(), totalOrderValue);

            Boolean paymentSuccess = (Boolean) rabbitTemplate.convertSendAndReceive(PAYMENT_EXCHANGE, PAYMENT_ROUTING_KEY,
                    orderPaymentMessage);
            System.out.println("The result is " + paymentSuccess);
            if(paymentSuccess != null && paymentSuccess) {
                Order savedOrder = orderRepository.save(order);

                OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(savedOrder.getNumber());

                String json = gson.toJson(orderPlacedEvent);

                kafkaTemplate.send(NOTIFICATION_TOPIC, json);

                return orderMapper.toDTO(savedOrder);
            } else {
                throw new PaymentException();
            }

        } else {
            throw new BadRequestException("Product/s not in stock");
        }
    }
}
