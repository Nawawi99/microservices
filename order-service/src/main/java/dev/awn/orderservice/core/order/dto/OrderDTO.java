package dev.awn.orderservice.core.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderDTO {
    private String id;
    private String number;
    private List<ItemDTO> items;
}
