package dev.jakapaw.giftcard.paymentmanager.application.service;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentEvent;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;
import dev.jakapaw.giftcard.paymentmanager.common.PaymentEventWrapper;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class EventHandler {

    PaymentEventDatastore paymentEventDatastore;

    public EventHandler(PaymentEventDatastore paymentEventDatastore) {
        this.paymentEventDatastore = paymentEventDatastore;
    }

    @EventListener(PaymentEventWrapper.class)
    public void on(PaymentEventWrapper<?> eventWrapper) {
        if (eventWrapper.getEvent().getClass() == PaymentInitiated.class) {
            handlePaymentInitiated((PaymentInitiated) eventWrapper.getEvent());
        }
    }

    private void handlePaymentInitiated(PaymentInitiated event) {
        PaymentEvent paymentEvent = new PaymentEvent(
                event.getPayment().getPaymentId(),
                1,
                event.getPayment()
        );
        event.getPayment().addEvent(paymentEvent);
    }
}
