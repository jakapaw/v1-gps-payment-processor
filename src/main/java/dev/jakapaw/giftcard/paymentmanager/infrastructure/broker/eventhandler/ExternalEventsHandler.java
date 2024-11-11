package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler;

import dev.jakapaw.giftcard.paymentmanager.application.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExternalEventsHandler {

    PaymentService paymentService;

    public ExternalEventsHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "series.payment")
    public void listen(SharedPaymentEvent event) {
        if (event.getPaymentStatus().compareTo(SharedPaymentStatus.PROCESSED.name()) == 0) {
            paymentService.completePayment(event.getGiftcardSerialNumber());
        } else {
            paymentService.declinePayment(event.getGiftcardSerialNumber());
        }
    }
}
