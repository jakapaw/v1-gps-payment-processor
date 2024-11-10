package dev.jakapaw.giftcard.paymentmanager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InitiatePayment{

    String giftcardId;
    String merchantId;
    double billed;
}
