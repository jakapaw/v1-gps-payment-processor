package dev.jakapaw.giftcard.paymentmanager.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;

/**
 * Payment Aggregate
 */
@Entity
@IdClass(PaymentId.class)
@Getter
public class Payment {

    @Id
    private String paymentId;
    @Id
    private Integer version;

    private String giftcard;
    private String merchantId;
    private double billAmount;

    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;

    private LocalDateTime createdAt;

    public Payment(String paymentId, Integer version, String giftcardSerialNumber, String merchantId, double billAmount, LocalDateTime paymentTime, PaymentState paymentStatus) {
        this.paymentId = paymentId;
        this.version = version;
        this.giftcard = giftcardSerialNumber;
        this.merchantId = merchantId;
        this.billAmount = billAmount;
        this.createdAt = paymentTime;
        this.paymentState = paymentStatus;
    }

    public Payment() {
    }

    public Payment updatePaymentState(PaymentState paymentStatus) {
        version++;
        this.paymentState = paymentStatus;
        this.createdAt = LocalDateTime.now();
        return this;
    }
}
