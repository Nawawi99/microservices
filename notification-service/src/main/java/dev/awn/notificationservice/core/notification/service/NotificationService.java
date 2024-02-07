package dev.awn.notificationservice.core.notification.service;

import dev.awn.notificationservice.core.notification.event.OrderPlacedEvent;

public interface NotificationService {
    void send(String orderPlacedEventJson);
}
