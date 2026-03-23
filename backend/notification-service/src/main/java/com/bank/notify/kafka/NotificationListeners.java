package com.bank.notify.kafka;

import com.bank.events.TransactionCompletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificationListeners {

    private static final Logger log = LoggerFactory.getLogger(NotificationListeners.class);

    private final ObjectMapper objectMapper;
    private final ObjectProvider<JavaMailSender> mailSender;
    private final boolean emailEnabled;

    public NotificationListeners(
            ObjectMapper objectMapper,
            ObjectProvider<JavaMailSender> mailSender,
            @Value("${notification.email.enabled:false}") boolean emailEnabled) {
        this.objectMapper = objectMapper;
        this.mailSender = mailSender;
        this.emailEnabled = emailEnabled;
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
            if (emailEnabled) {
                mailSender.ifAvailable(
                        sender -> {
                            try {
                                var msg = new SimpleMailMessage();
                                msg.setTo("customer@example.invalid");
                                msg.setFrom("noreply@bank.local");
                                msg.setSubject("Transaction completed");
                                msg.setText(
                                        "Tx "
                                                + evt.transactionId()
                                                + " amount "
                                                + evt.amount()
                                                + " "
                                                + evt.currency());
                                sender.send(msg);
                            } catch (Exception ex) {
                                log.warn("Mail send skipped: {}", ex.getMessage());
                            }
                        });
            }
        } catch (Exception e) {
            log.warn("Notification parse error: {}", e.getMessage());
        }
    }
}
