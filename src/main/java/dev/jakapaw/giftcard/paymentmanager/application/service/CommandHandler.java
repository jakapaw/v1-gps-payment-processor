package dev.jakapaw.giftcard.paymentmanager.application.service;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;
import dev.jakapaw.giftcard.paymentmanager.common.PaymentEventWrapper;
import dev.jakapaw.giftcard.paymentmanager.common.OngoingPaymentRegistry;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.KafkaProducer;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Async
    public CompletableFuture<Payment> initiatePayment(InitiatePaymentCommand command) {
        String paymentId = generatePaymentId(command.getMerchantId());
        Payment payment = new Payment(
                paymentId,
                command.getGiftcardSerialNumber(),
                command.getMerchantId(),
                command.getBillAmount(),
                LocalDateTime.now(),
                PaymentStatus.ON_PROCESS
        );
        kafkaProducer.publishVerificationEvent(payment);

        PaymentInitiated paymentInitiated = new PaymentInitiated(payment);
        applicationEventPublisher.publishEvent(new PaymentEventWrapper<>(this, paymentInitiated));

        CompletableFuture<Payment> completableFuture = new CompletableFuture<>();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.submit(() -> {
                // make thread wait for payment transaction to complete
                while (!ongoingPaymentRegistry.isPaymentComplete(paymentId)) {}
                Payment paymentFinished = ongoingPaymentRegistry.removeOngoingPayment(paymentId);
                paymentEventDatastore.saveAll(paymentFinished.getPaymentEvents());
                paymentFinished.clearEvents();
                completableFuture.complete(paymentFinished);
            });
        }
        return completableFuture;
    }

    private String generatePaymentId(String merchantId) {
        Random random = new Random();
        return merchantId + String.format("%02d", random.nextLong(10000,1000000));
    }
}
