package dev.awn.orderservice.core.order.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Item {
    private String code;
    private Integer quantity;
    private Double price;
}
