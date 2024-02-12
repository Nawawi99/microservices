package dev.awn.paymentservice.core.payment.service;

import dev.awn.paymentservice.core.payment.message.OrderPaymentMessage;

public interface PaymentService {
    boolean pay(OrderPaymentMessage orderPaymentMessage);
}
