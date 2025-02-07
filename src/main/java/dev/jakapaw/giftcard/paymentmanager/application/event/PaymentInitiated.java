package dev.jakapaw.giftcard.paymentmanager.application.event;

import java.util.EventObject;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import lombok.Getter;

@Getter
public class PaymentInitiated extends EventObject {

    private Payment payment;

    public PaymentInitiated(Object source, Payment payment) {
        super(source);
        this.payment = payment;
    }

}
