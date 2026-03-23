package com.bank.account.kafka;

import com.bank.account.service.AccountService;
import com.bank.events.TransactionCompletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "day2.kafka.consumer.enabled", havingValue = "true", matchIfMissing = true)
public class TransactionCompletedConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransactionCompletedConsumer.class);

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public TransactionCompletedConsumer(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${day2.kafka.topics.transaction-completed:bank.transaction.completed}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(String payload) {
        try {
            TransactionCompletedEvent event = objectMapper.readValue(payload, TransactionCompletedEvent.class);
            accountService.applyBalanceMovement(
                    event.accountId(), event.amount(), event.type(), event.currency());
            log.debug("Applied transaction completed event for account {}", event.accountId());
        } catch (Exception e) {
            log.error("Failed to process TransactionCompleted: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
