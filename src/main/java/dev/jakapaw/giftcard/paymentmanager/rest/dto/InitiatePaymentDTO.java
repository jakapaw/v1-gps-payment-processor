package dev.jakapaw.giftcard.paymentmanager.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InitiatePaymentDTO {

    String giftcardSerialNumber;
    String merchantId;
    double billed;
}
