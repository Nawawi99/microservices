package dev.awn.paymentservice.core.payment.service.impl;

import dev.awn.paymentservice.core.payment.message.OrderPaymentMessage;
import dev.awn.paymentservice.core.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @RabbitListener(queues = "payment-queue")
    @Override
    public boolean pay(OrderPaymentMessage orderPaymentMessage) {
        log.info("Paying order of number {} and amount of {}",
                orderPaymentMessage.getOrderNumber(), orderPaymentMessage.getAmount());

        return true;
    }
}
