package dev.jakapaw.giftcard.paymentmanager.rest.dto;

import java.time.LocalDateTime;

import dev.jakapaw.giftcard.paymentmanager.domain.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentDetailDTO {

    private String paymentId;
    private String giftcardSerialNumber;
    private String merchantId;
    private double billAmount;
    private LocalDateTime paymentTime;
    private PaymentState paymentStatus;
}
