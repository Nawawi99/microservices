package dev.awn.productservice.core.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private String id;

    @NotNull(message = "Product name cannot be empty")
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Product price cannot be empty")
    @Min(value = 0, message = "Product price cannot be less than 0")
    private Double price;
}
