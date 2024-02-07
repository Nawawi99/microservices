package dev.awn.productservice.core.product.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "products")
public class Product {
    private String id;
    private String name;
    private String description;
    private Double price;
}
