package dev.jakapaw.giftcard.paymentmanager.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import dev.jakapaw.giftcard.paymentmanager.application.query.GetGiftcardStateQuery;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;

@EnableAsync
@Service
public class QueryHandler {

    PaymentEventDatastore paymentEventDatastore;

    @Async
    public CompletableFuture<List<Payment[]>> getPaymentHistory(String giftcardNumber) {
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            return CompletableFuture.supplyAsync(() -> {
                List<Payment> events = paymentEventDatastore.findByGiftcard(giftcardNumber);    // payment events

                List<Payment[]> groupedPayments = new ArrayList<>();
                Payment[] arr = new Payment[3];
                String prevPaymentId = "";
                int arr_i = 0;
                for (int i = 0; i < events.size(); i++) {
                    String paymentId = events.get(i).getPaymentId();
                    if (paymentId.equals(prevPaymentId)) {
                        arr[arr_i++] = events.get(i);
                    } else {
                        if (i > 0) groupedPayments.add(arr);
                        arr = new Payment[3];
                        arr_i = 0;
                        prevPaymentId = events.get(i).getPaymentId();
                        arr[arr_i++] = events.get(i);
                    }
                }
                return groupedPayments;
            }, exec);
        }
    }

    public CompletableFuture<Double> getGiftcardBalanceAtTime(GetGiftcardStateQuery query) {
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            return CompletableFuture.supplyAsync(() -> {
                List<Payment> events = paymentEventDatastore.findByGiftcardAndCreatedAtBetween(
                        query.getGiftcardSerialNumber(),
                        query.getStartTime(),
                        query.getEndTime());
                double finalBalance = query.getBalance();
                for (var event : events) {
                    finalBalance -= event.getBillAmount();
                }
                return finalBalance;
            }, exec);
        }
    }
}
