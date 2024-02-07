package dev.awn.notificationservice.core.notification.event;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class OrderPlacedEvent implements Serializable {
    private String number;
}
