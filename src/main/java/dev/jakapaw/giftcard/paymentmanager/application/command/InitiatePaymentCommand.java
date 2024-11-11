package dev.jakapaw.giftcard.paymentmanager.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InitiatePaymentCommand {

    private String giftcardSerialNumber;
    private String merchantId;
    private double billAmount;
}
