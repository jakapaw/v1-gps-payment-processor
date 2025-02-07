package dev.jakapaw.giftcard.paymentmanager.application.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentAccepted;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentDeclined;
import dev.jakapaw.giftcard.paymentmanager.application.query.GetGiftcardStateQuery;
import dev.jakapaw.giftcard.paymentmanager.application.query.GetPaymentHistoryQuery;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentState;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.KafkaProducer;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;

/*
Payment Service contains all business operation of payment processing
 */
@Service
public class PaymentService implements ApplicationEventPublisherAware {

    ApplicationEventPublisher applicationEventPublisher;
    KafkaProducer kafkaProducer;
    PaymentEventDatastore paymentEventDatastore;
    CommandHandler commandHandler;
    QueryHandler queryHandler;

    public PaymentService(KafkaProducer kafkaProducer, PaymentEventDatastore paymentEventDatastore, CommandHandler commandHandler) {
        this.kafkaProducer = kafkaProducer;
        this.paymentEventDatastore = paymentEventDatastore;
        this.commandHandler = commandHandler;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Payment initiatePayment(InitiatePaymentCommand command) {
        return commandHandler.initiatePayment(command);
    }

    public CompletableFuture<List<Payment[]>> getPaymentHistory(String giftcardNumber) {
        // TODO: use Query class
        GetPaymentHistoryQuery query = new GetPaymentHistoryQuery(this, giftcardNumber);
        return queryHandler.getPaymentHistory(giftcardNumber);
    }

    public CompletableFuture<Double> getGiftcardStateAtTime(GetGiftcardStateQuery query) {
        return queryHandler.getGiftcardBalanceAtTime(query);
    }

    public void declinePayment(String paymentId) {
        PaymentDeclined event = new PaymentDeclined(this, paymentId, PaymentState.PAYMENT_DECLINED);
        applicationEventPublisher.publishEvent(event);
    }

    public void completePayment(String paymentId) {
        PaymentAccepted event = new PaymentAccepted(this, paymentId, PaymentState.PAYMENT_ACCEPTED);
        applicationEventPublisher.publishEvent(event);
    }
}
