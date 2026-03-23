package com.bank.transaction.kafka;

import com.bank.transaction.repository.TransactionEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(name = "day2.kafka.producer.enabled", havingValue = "true", matchIfMissing = false)
public class OutboxEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(OutboxEventPublisher.class);

    private final TransactionEventRepository eventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${day2.kafka.topics.transaction-created:bank.transaction.created}")
    private String topicCreated;

    @Value("${day2.kafka.topics.transaction-completed:bank.transaction.completed}")
    private String topicCompleted;

    public OutboxEventPublisher(
            TransactionEventRepository eventRepository,
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelayString = "${day2.outbox.publish-interval-ms:2000}")
    @Transactional
    public void publishPending() {
        var batch = eventRepository.findTop100ByPublishedFalseOrderByCreatedAtAsc();
        for (var ev : batch) {
            try {
                String topic = resolveTopic(ev.getEventType());
                JsonNode root = objectMapper.readTree(ev.getPayload());
                String key = root.hasNonNull("transactionId")
                        ? root.get("transactionId").asText()
                        : ev.getAggregateId().toString();
                kafkaTemplate.send(topic, key, ev.getPayload()).get();
                ev.setPublished(true);
            } catch (Exception e) {
                log.warn("Outbox publish failed for {}: {}", ev.getId(), e.getMessage());
            }
        }
        eventRepository.saveAll(batch);
    }

    private String resolveTopic(String eventType) {
        if ("TRANSACTION_COMPLETED".equals(eventType)) {
            return topicCompleted;
        }
        return topicCreated;
    }
}
