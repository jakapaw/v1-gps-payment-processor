package dev.jakapaw.giftcard.paymentmanager.application.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentAccepted;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentDeclined;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentId;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentState;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.KafkaProducer;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.webhook.PaymentWebhook;

@Service
public class EventHandler {

    PaymentEventDatastore paymentEventDatastore;
    KafkaProducer kafkaProducer;
    PaymentWebhook paymentWebhook;

    public EventHandler(PaymentEventDatastore paymentEventDatastore, KafkaProducer kafkaProducer,
            PaymentWebhook paymentWebhook) {
        this.paymentEventDatastore = paymentEventDatastore;
        this.kafkaProducer = kafkaProducer;
        this.paymentWebhook = paymentWebhook;
    }

    @EventListener(PaymentInitiated.class)
    public void on(PaymentInitiated event) {
        paymentEventDatastore.save(event.getPayment());
        kafkaProducer.publishVerificationEvent(event.getPayment());
    }

    @EventListener(PaymentDeclined.class)
    public void on(PaymentDeclined event) {
        paymentEventDatastore.findById(new PaymentId(event.getPaymentId(), 1))
            .ifPresentOrElse(
                payment -> {
                    Payment finalPayment = payment.updatePaymentState(PaymentState.PAYMENT_DECLINED);
                    paymentEventDatastore.save(finalPayment);
                    paymentWebhook.notifyPaymentDeclined(finalPayment);
                }, 
                () -> {
                    paymentWebhook.notifyPaymentError();
                });
    }

    @EventListener(PaymentAccepted.class)
    public void on(PaymentAccepted event) {
        paymentEventDatastore.findById(new PaymentId(event.getPaymentId(), 1))
        .ifPresentOrElse(
            payment -> {
                Payment finalPayment = payment.updatePaymentState(PaymentState.PAYMENT_ACCEPTED);
                paymentEventDatastore.save(finalPayment);
                paymentWebhook.notifyPaymentAccepted(finalPayment);
            }, 
            () -> {
                paymentWebhook.notifyPaymentError();
            });
    }
}
