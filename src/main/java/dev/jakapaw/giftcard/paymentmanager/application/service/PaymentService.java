package dev.jakapaw.giftcard.paymentmanager.application.service;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentCompleted;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentDeclined;
import dev.jakapaw.giftcard.paymentmanager.application.query.GetPaymentHistoryQuery;
import dev.jakapaw.giftcard.paymentmanager.common.OngoingPaymentRegistry;
import dev.jakapaw.giftcard.paymentmanager.common.PaymentEventWrapper;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentStatus;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.KafkaProducer;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/*
Payment Service contains all business operation of payment processing
 */

@Service
public class PaymentService implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;
    KafkaProducer kafkaProducer;
    OngoingPaymentRegistry ongoingPaymentRegistry;
    PaymentEventDatastore paymentEventDatastore;
    CommandHandler commandHandler;
    QueryHandler queryHandler;

    public PaymentService(KafkaProducer kafkaProducer, OngoingPaymentRegistry ongoingPaymentRegistry, PaymentEventDatastore paymentEventDatastore, CommandHandler commandHandler) {
        this.kafkaProducer = kafkaProducer;
        this.ongoingPaymentRegistry = ongoingPaymentRegistry;
        this.paymentEventDatastore = paymentEventDatastore;
        this.commandHandler = commandHandler;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public CompletableFuture<Payment> initiatePayment(InitiatePaymentCommand command) {
        return commandHandler.initiatePayment(command);
    }

    public CompletableFuture<List<Payment[]>> getPaymentHistory(String giftcardNumber) {
        GetPaymentHistoryQuery query = new GetPaymentHistoryQuery(this, giftcardNumber);
        return queryHandler.getPaymentHistory(giftcardNumber);
    }

    public void declinePayment(String paymentId) {
        PaymentDeclined event = new PaymentDeclined(paymentId, PaymentStatus.DECLINED);
        applicationEventPublisher.publishEvent(new PaymentEventWrapper<>(this, event));
    }

    public void completePayment(String paymentId) {
        PaymentCompleted event = new PaymentCompleted(paymentId, PaymentStatus.COMPLETED);
        applicationEventPublisher.publishEvent(new PaymentEventWrapper<>(this, event));
    }
}
