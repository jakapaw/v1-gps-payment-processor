package dev.jakapaw.giftcard.paymentmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Payment {

    private String paymentId;
    private String giftcardSerialNumber;
    private String merchantId;
    private double billAmount;
    private LocalDateTime paymentTime;
    @Setter
    private PaymentStatus paymentStatus;
}
