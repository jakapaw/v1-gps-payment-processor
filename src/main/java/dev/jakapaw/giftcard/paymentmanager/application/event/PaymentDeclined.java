package dev.jakapaw.giftcard.paymentmanager.application.event;

import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PaymentDeclined {

    private String paymentId;
    private PaymentStatus paymentStatus;
}
