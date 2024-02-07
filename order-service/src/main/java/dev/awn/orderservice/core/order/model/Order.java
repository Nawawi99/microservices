package dev.awn.orderservice.core.order.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "orders")
public class Order {
    @Id
    private String id;
    private String number;
    private List<Item> items;
}
