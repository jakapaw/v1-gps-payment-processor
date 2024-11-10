package dev.jakapaw.giftcard.paymentmanager.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InitializePaymentCommand {

    private String paymentId;
    private String giftcardId;
    private String merchantId;
    private double billed;
}
