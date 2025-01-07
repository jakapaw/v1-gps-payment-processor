package dev.jakapaw.giftcard.paymentmanager.application.service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;
import dev.jakapaw.giftcard.paymentmanager.common.OngoingPaymentRegistry;
import dev.jakapaw.giftcard.paymentmanager.common.PaymentEvent;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentState;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.KafkaProducer;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;

@EnableAsync
@Service
public class CommandHandler implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;
    KafkaProducer kafkaProducer;
    OngoingPaymentRegistry ongoingPaymentRegistry;
    PaymentEventDatastore paymentEventDatastore;

    public CommandHandler(KafkaProducer kafkaProducer, OngoingPaymentRegistry ongoingPaymentRegistry, PaymentEventDatastore paymentEventDatastore) {
        this.kafkaProducer = kafkaProducer;
        this.ongoingPaymentRegistry = ongoingPaymentRegistry;
        this.paymentEventDatastore = paymentEventDatastore;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public CompletableFuture<Payment> initiatePayment(InitiatePaymentCommand command) {
        String paymentId = generatePaymentId(command.getMerchantId());
        Payment payment = new Payment(
                paymentId,
                command.getGiftcardSerialNumber(),
                command.getMerchantId(),
                command.getBillAmount(),
                LocalDateTime.now(),
                PaymentState.PAYMENT_INITIALIZED
        );
        kafkaProducer.publishVerificationEvent(payment);

        PaymentInitiated paymentInitiated = new PaymentInitiated(payment);
        applicationEventPublisher.publishEvent(new PaymentEvent<>(this, paymentInitiated));

        CompletableFuture<Payment> completableFuture = new CompletableFuture<>();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.submit(() -> {
                // make thread wait for payment transaction to complete
                Payment ongoingPayment = ongoingPaymentRegistry.getOngoingPayment(paymentId);
                while (!ongoingPaymentRegistry.isPaymentComplete(paymentId)) {}
                completableFuture.complete(ongoingPayment);
            });
        }
        return completableFuture;
    }

    private String generatePaymentId(String merchantId) {
        Random random = new Random();
        return merchantId + String.format("%02d", random.nextLong(10000,1000000));
    }
}
