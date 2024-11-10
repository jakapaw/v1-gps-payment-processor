package dev.jakapaw.giftcard.paymentmanager.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentDetail {

    private String giftcardId;
    private String merchantId;
    private double billed;
    private String paymentStatus;
}
