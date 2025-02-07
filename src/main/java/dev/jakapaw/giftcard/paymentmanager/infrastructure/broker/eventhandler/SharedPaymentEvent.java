package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler;

import lombok.Getter;

@Getter
public class SharedPaymentEvent {

    public enum PaymentStatus {
        ACCEPTED,
        DECLINED
    }

    private String paymentId;
    private String giftcardSerialNumber;
    private double billAmount;
    private double finalBalance;
    private String paymentStatus;

    public SharedPaymentEvent() {
    }

    public SharedPaymentEvent(String paymentId, String giftcardSerialNumber, double billAmount, double finalBalance, String paymentStatus) {
        this.paymentId = paymentId;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
        this.finalBalance = finalBalance;
        this.paymentStatus = paymentStatus;
    }

    public SharedPaymentEvent(String paymentId, String giftcardSerialNumber, double billAmount, String paymentStatus) {
        this.paymentId = paymentId;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.billAmount = billAmount;
        this.paymentStatus = paymentStatus;
    }
}
