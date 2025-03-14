package dev.jakapaw.giftcard.paymentmanager.application.event;

import java.util.EventObject;

import dev.jakapaw.giftcard.paymentmanager.domain.PaymentState;
import lombok.Getter;

@Getter
public class PaymentDeclined extends EventObject {

    private String paymentId;
    private PaymentState paymentStatus;

    public PaymentDeclined(Object source, String paymentId, PaymentState paymentStatus) {
        super(source);
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
    }

}
