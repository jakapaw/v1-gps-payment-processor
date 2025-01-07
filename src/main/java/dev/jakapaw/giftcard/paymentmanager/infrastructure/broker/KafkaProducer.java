package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler.SharedPaymentEvent;

@Service
public class KafkaProducer {

    KafkaTemplate<String, Object> kafkaTemplateJson;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplateJson) {
        this.kafkaTemplateJson = kafkaTemplateJson;
    }

    public void publishVerificationEvent(Payment payment) {
        SharedPaymentEvent event = new SharedPaymentEvent(
                payment.getGiftcard(), payment.getBillAmount(), payment.getPaymentStatus().name());
        kafkaTemplateJson.send("payment.verification.start", event);
    }
}
