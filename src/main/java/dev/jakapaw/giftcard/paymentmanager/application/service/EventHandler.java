package dev.jakapaw.giftcard.paymentmanager.application.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;
import dev.jakapaw.giftcard.paymentmanager.common.PaymentEvent;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;

@Service
public class EventHandler {

    PaymentEventDatastore paymentEventDatastore;

    public EventHandler(PaymentEventDatastore paymentEventDatastore) {
        this.paymentEventDatastore = paymentEventDatastore;
    }

    @EventListener(PaymentEvent.class)
    public void on(PaymentEvent<?> eventWrapper) {
        if (eventWrapper.getEvent().getClass() == PaymentInitiated.class) {
            handlePaymentInitiated((PaymentInitiated) eventWrapper.getEvent());
        }
    }

    private void handlePaymentInitiated(PaymentInitiated event) {
        paymentEventDatastore.save(event.getPayment());
    }
}
