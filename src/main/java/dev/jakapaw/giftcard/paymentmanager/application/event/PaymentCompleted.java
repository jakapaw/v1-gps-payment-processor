package dev.jakapaw.giftcard.paymentmanager.application.event;

import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentCompleted {

    private String paymentId;
    private PaymentStatus paymentStatus;
}
