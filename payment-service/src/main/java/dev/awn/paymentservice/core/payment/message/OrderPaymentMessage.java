package dev.awn.paymentservice.core.payment.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentMessage {
    private String orderNumber;
    private Double amount;
}