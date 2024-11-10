package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler.PaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    KafkaTemplate<String, Object> kafkaTemplateJson;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplateJson) {
        this.kafkaTemplateJson = kafkaTemplateJson;
    }

    public void publishVerificationEvent(Payment payment) {
        PaymentEvent event = new PaymentEvent(payment.getGiftcardSerialNumber(), payment.getBillAmount(), false);
        kafkaTemplateJson.send("series.payment", event);
    }
}
