package com.bank.fraud.kafka;

import com.bank.events.TransactionCreatedEvent;
import com.bank.fraud.service.RuleEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedListener.class);

    private final ObjectMapper objectMapper;
    private final RuleEngine ruleEngine;

    public TransactionCreatedListener(ObjectMapper objectMapper, RuleEngine ruleEngine) {
        this.objectMapper = objectMapper;
        this.ruleEngine = ruleEngine;
    }

    @KafkaListener(
            topics = "${day3.kafka.topics.transaction-created:bank.transaction.created}",
            groupId = "${spring.kafka.consumer.group-id:fraud-detection}")
    public void onMessage(String payload) {
        try {
            TransactionCreatedEvent evt = objectMapper.readValue(payload, TransactionCreatedEvent.class);
            var verdict =
                    ruleEngine.evaluateSync(
                            evt.accountId().toString(),
                            evt.amount(),
                            evt.currency(),
                            "kafka-async");
            log.info(
                    "Fraud async check tx={} account={} verdict={}",
                    evt.transactionId(),
                    evt.accountId(),
                    verdict);
        } catch (Exception e) {
            log.warn("Fraud listener parse error: {}", e.getMessage());
        }
    }
}
