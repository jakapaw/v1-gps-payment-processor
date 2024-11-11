package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler.SharedPaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    KafkaTemplate<String, Object> kafkaTemplateJson;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplateJson) {
        this.kafkaTemplateJson = kafkaTemplateJson;
    }

    public void publishVerificationEvent(Payment payment) {
        SharedPaymentEvent event = new SharedPaymentEvent(
                payment.getGiftcardSerialNumber(), payment.getBillAmount(), payment.getPaymentStatus().name());
        kafkaTemplateJson.send("series.payment", event);
    }
}
