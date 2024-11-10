package dev.jakapaw.giftcard.paymentmanager.application.event;

import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentDeclined {

    private String paymentId;
    private PaymentStatus paymentStatus;
}
