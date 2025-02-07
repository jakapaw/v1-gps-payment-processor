package dev.jakapaw.giftcard.paymentmanager.application.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentState;

@Service
public class CommandHandler implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Payment initiatePayment(InitiatePaymentCommand command) {
        String paymentId = generatePaymentId(command.getMerchantId());
        Payment payment = new Payment(
                paymentId,
                1,
                command.getGiftcardSerialNumber(),
                command.getMerchantId(),
                command.getBillAmount(),
                LocalDateTime.now(),
                PaymentState.PAYMENT_ON_PROCESS
        );

        PaymentInitiated paymentInitiated = new PaymentInitiated(this, payment);
        applicationEventPublisher.publishEvent(paymentInitiated);

        return payment;
    }

    private String generatePaymentId(String merchantId) {
        Random random = new Random();
        return merchantId + String.format("%02d", random.nextLong(10000,1000000));
    }
}
