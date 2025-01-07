package dev.jakapaw.giftcard.paymentmanager.common;

import java.util.HashMap;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentCompleted;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentDeclined;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;

@Service
public class OngoingPaymentRegistry {

    private final HashMap<String, Payment> ongoingPayments = new HashMap<>();

    public void addOngoingPayment(Payment payment) {
        ongoingPayments.put(payment.getPaymentId(), payment);
    }

    @EventListener({PaymentCompleted.class, PaymentDeclined.class})
    public void setPaymentStatus(Object event) {
        if (event.getClass() == PaymentCompleted.class) {
            PaymentCompleted paymentCompleted  = (PaymentCompleted) event;
            Payment p = ongoingPayments.get(paymentCompleted.getPaymentId());
            p.updatePaymentState(paymentCompleted.getPaymentStatus());

        } else if (event.getClass() == PaymentDeclined.class) {
            PaymentDeclined paymentDeclined  = (PaymentDeclined) event;
            Payment p = ongoingPayments.get(paymentDeclined.getPaymentId());
            p.updatePaymentState(paymentDeclined.getPaymentStatus());
        }
    }

    public boolean isPaymentComplete(String paymentId) {
        return ongoingPayments.containsKey(paymentId);
    }

    public Payment removeOngoingPayment(String paymentId) {
        return ongoingPayments.remove(paymentId);
    }

    public Payment getOngoingPayment(String paymentId) {
        return ongoingPayments.get(paymentId);
    }
}
