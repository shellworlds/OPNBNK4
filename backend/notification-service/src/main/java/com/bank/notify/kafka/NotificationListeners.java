package com.bank.notify.kafka;

import com.bank.events.TransactionCompletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListeners {

    private static final Logger log = LoggerFactory.getLogger(NotificationListeners.class);

    private final ObjectMapper objectMapper;

    public NotificationListeners(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "${day3.kafka.topics.transaction-completed:bank.transaction.completed}",
            groupId = "${spring.kafka.consumer.group-id:notification-service}")
    public void onTxCompleted(String payload) {
        try {
            TransactionCompletedEvent evt = objectMapper.readValue(payload, TransactionCompletedEvent.class);
            log.info(
                    "[STUB] Would send push/email for completed tx {} account {} amount {} {}",
                    evt.transactionId(),
                    evt.accountId(),
                    evt.amount(),
                    evt.currency());
        } catch (Exception e) {
            log.warn("Notification parse error: {}", e.getMessage());
        }
    }
}
