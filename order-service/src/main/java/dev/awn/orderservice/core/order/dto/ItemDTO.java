package dev.awn.orderservice.core.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ItemDTO {
    @NotNull
    @NotBlank(message = "Item code should be present")
    private String code;

    @NotNull(message = "Quantity cannot be empty")
    @Min(value = 1, message = "Quantity should be at least 1")
    private Integer quantity;

    @NotNull(message = "Order price cannot be empty")
    @Min(value = 0, message = "Order price cannot be less than 0")
    private Double price;
}
