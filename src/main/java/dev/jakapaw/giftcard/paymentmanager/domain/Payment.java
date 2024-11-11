package dev.jakapaw.giftcard.paymentmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentEvent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
public class Payment {

    @JsonIgnore
    private Queue<PaymentEvent> paymentEvents;

    private String paymentId;
    private String giftcardSerialNumber;
    private String merchantId;
    private double billAmount;
    private LocalDateTime paymentTime;
    @Setter
    private PaymentStatus paymentStatus;

    public Payment(String paymentId, String giftcardSerialNumber, String merchantId, double billAmount, LocalDateTime paymentTime, PaymentStatus paymentStatus) {
        this.paymentId = paymentId;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.merchantId = merchantId;
        this.billAmount = billAmount;
        this.paymentTime = paymentTime;
        this.paymentStatus = paymentStatus;
        this.paymentEvents = new ConcurrentLinkedQueue<>();
    }

    public void addEvent(PaymentEvent event) {
        paymentEvents.add(event);
    }

    public void clearEvents() {
        paymentEvents.clear();
    }
}
