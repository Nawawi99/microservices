package dev.awn.notificationservice.core.notification.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import dev.awn.notificationservice.core.notification.event.OrderPlacedEvent;
import dev.awn.notificationservice.core.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final String NOTIFICATION_TOPIC = "NOTIFICATION_TOPIC";
    private final String NOTIFICATION_TOPIC_CONSUMER_GROUP = "NOTIFICATION_TOPIC_CONSUMER_GROUP";
    private final Gson gson;

    @KafkaListener(topics = NOTIFICATION_TOPIC,
            groupId = NOTIFICATION_TOPIC_CONSUMER_GROUP)
    @Override
    public void send(String orderPlacedEventJson) {
        OrderPlacedEvent orderPlacedEvent = gson.fromJson(orderPlacedEventJson,
                OrderPlacedEvent.class);

        System.err.println("Notification of order number [" +
                orderPlacedEvent.getNumber()
                + "] was sent successfully");

        System.err.println("Notification Sent");
    }
}
