package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler.SharedPaymentEvent;

@Component
public class KafkaProducer {

    KafkaTemplate<String, Object> kafkaTemplateJson;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplateJson) {
        this.kafkaTemplateJson = kafkaTemplateJson;
    }

    public void publishVerificationEvent(Payment payment) {
        SharedPaymentEvent event = new SharedPaymentEvent(
                payment.getPaymentId(), payment.getGiftcard(), payment.getBillAmount(), payment.getPaymentState().name());
        kafkaTemplateJson.send("payment.process.start", event);
    }
}
