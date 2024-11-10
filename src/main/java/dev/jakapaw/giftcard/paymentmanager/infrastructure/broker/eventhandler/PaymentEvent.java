package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentEvent {

    private String giftcardSerialNumber;
    private double billAmount;
    private boolean isVerified;
}
