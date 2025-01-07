package dev.jakapaw.giftcard.paymentmanager.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;

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
    private PaymentState paymentStatus;
    private LocalDateTime paymentTime;
    private LocalDateTime updateTime;

    public Payment(String paymentId, String giftcardSerialNumber, String merchantId, double billAmount, LocalDateTime paymentTime, PaymentState paymentStatus) {
        this.paymentId = paymentId;
        this.giftcard = giftcardSerialNumber;
        this.merchantId = merchantId;
        this.billAmount = billAmount;
        this.paymentTime = paymentTime;
        this.paymentStatus = paymentStatus;
    }

    public Payment updatePaymentState(PaymentState paymentStatus) {
        this.version++;
        this.paymentStatus = paymentStatus;
        this.updateTime = LocalDateTime.now();
        return this;
    }
}
