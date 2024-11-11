package dev.jakapaw.giftcard.paymentmanager.application.event;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PaymentInitiated {

    private Payment payment;
}
