package dev.jakapaw.giftcard.paymentmanager.application.service;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentEvent;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventHandler {

    PaymentEventDatastore paymentEventDatastore;

    public EventHandler(PaymentEventDatastore paymentEventDatastore) {
        this.paymentEventDatastore = paymentEventDatastore;
    }

    @EventListener(PaymentInitiated.class)
    public void on(PaymentInitiated event) {
        PaymentEvent paymentEvent = new PaymentEvent(
                event.getPayment().getPaymentId(),
                1,
                event.getPayment()
        );
        event.getPayment().addEvent(paymentEvent);
    }
}
