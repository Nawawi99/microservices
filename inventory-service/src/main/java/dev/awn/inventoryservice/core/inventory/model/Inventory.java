package dev.awn.inventoryservice.core.inventory.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "inventory")
@ToString
public class Inventory {
    @Id
    private String id;
    private String code;
    private Long quantity;
}
