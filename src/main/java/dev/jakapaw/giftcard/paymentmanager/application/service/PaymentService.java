package dev.jakapaw.giftcard.paymentmanager.application.service;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitializePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;

    public String initiatePayment(String giftcardId, String merchantId, double billed) {
        String paymentId = generatePaymentId(merchantId);
        InitializePaymentCommand command = new InitializePaymentCommand(
                paymentId,
                giftcardId,
                merchantId,
                billed
        );
        applicationEventPublisher.publishEvent(command);
        return PaymentStatus.ON_VERIFICATION.name();
    }

    private String generatePaymentId(String merchantId) {
        Random random = new Random();
        return merchantId + String.format("%02d", random.nextLong(10000,1000000));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
