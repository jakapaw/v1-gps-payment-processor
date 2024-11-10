package dev.jakapaw.giftcard.paymentmanager.domain;

import jakarta.persistence.Id;
import jakarta.persistence.Version;

import java.time.LocalDateTime;

public class Payment {

    @Id
    private String paymentId;
    @Version
    private Integer version;
    private String giftcardId;
    private String merchantId;
    private double billAmount;
    private LocalDateTime paymentTime;
    private PaymentStatus paymentStatus;
}
