package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.service.PaymentService;

@Service
public class ExternalEventsHandler {

    PaymentService paymentService;

    public ExternalEventsHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "payment.process.done")
    public void listen(SharedPaymentEvent event) {
        if (event.getPaymentStatus().compareTo(SharedPaymentStatus.PROCESSED.name()) == 0) {
            paymentService.completePayment(event.getGiftcardSerialNumber());
        } else {
            paymentService.declinePayment(event.getGiftcardSerialNumber());
        }
    }
}
