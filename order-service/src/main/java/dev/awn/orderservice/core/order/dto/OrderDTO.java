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

//    @NotNull(message = "Order should contain item/s")
//    @NotEmpty(message = "Order should contain item/s")
    private List<ItemDTO> items;
}
