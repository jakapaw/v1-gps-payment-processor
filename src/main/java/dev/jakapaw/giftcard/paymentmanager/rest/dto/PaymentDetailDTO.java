package dev.jakapaw.giftcard.paymentmanager.rest.dto;

import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PaymentDetailDTO {

    private String paymentId;
    private String giftcardSerialNumber;
    private String merchantId;
    private double billAmount;
    private LocalDateTime paymentTime;
    private PaymentStatus paymentStatus;
}
