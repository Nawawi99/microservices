package dev.awn.orderservice.core.order.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class InventoryResponse {
    private String code;
    private Boolean inStock;
}
