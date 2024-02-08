package dev.awn.orderservice.core.order.controller;

import dev.awn.orderservice.common.exception.ServiceUnavailableException;
import dev.awn.orderservice.core.order.dto.OrderDTO;
import dev.awn.orderservice.core.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final String INVENTORY_SERVICE_CIRCUIT = "inventory-service-circuit";

    private final OrderService orderService;

    /**
     * This Call is STILL SYNC, the inner logic is sync, the request is
     * wrapped by a CompletableFuture to guarantee either 2 options
     * 1- Response is returned to the caller, the caller waits for it
     * 2- Timeout exceeded and the call is aborted
     */
    @PostMapping
    @CircuitBreaker(name = INVENTORY_SERVICE_CIRCUIT, fallbackMethod = "createOrderFallback")
    @TimeLimiter(name = INVENTORY_SERVICE_CIRCUIT)
    @Retry(name = INVENTORY_SERVICE_CIRCUIT)
    public CompletableFuture<ResponseEntity<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) {
        return CompletableFuture
                .supplyAsync(() -> new ResponseEntity<>(
                        orderService.createOrder(orderDTO),
                        HttpStatus.CREATED)
                );
    }

    public CompletableFuture<ResponseEntity<OrderDTO>> createOrderFallback(OrderDTO orderDTO, RuntimeException e) {
        return CompletableFuture.supplyAsync(() -> {
            throw new ServiceUnavailableException("Oops! Something went wrong, try again later");
        });
    }

}
