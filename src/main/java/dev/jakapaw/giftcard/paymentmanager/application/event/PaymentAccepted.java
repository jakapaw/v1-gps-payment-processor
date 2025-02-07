package dev.jakapaw.giftcard.paymentmanager.application.event;

import java.util.EventObject;

import dev.jakapaw.giftcard.paymentmanager.domain.PaymentState;
import lombok.Getter;

@Getter
public class PaymentAccepted extends EventObject {

    private String paymentId;
    private PaymentState paymentStatus;
    
    public PaymentAccepted(Object source, String paymentId, PaymentState paymentStatus) {
        super(source);
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
    }
}
