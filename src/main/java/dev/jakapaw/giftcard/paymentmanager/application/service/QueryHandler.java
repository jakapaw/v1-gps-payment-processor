package dev.jakapaw.giftcard.paymentmanager.application.service;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentEvent;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.infrastructure.repository.PaymentEventDatastore;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@EnableAsync
@Service
public class QueryHandler {

    PaymentEventDatastore paymentEventDatastore;

    @Async
    public CompletableFuture<List<Payment[]>> getPaymentHistory(String giftcardNumber) {
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {
            return CompletableFuture.supplyAsync(() -> {
                List<PaymentEvent> events = paymentEventDatastore.getPaymentEventsByGiftcardSerialNumber(giftcardNumber);

                List<Payment[]> groupedPayments = new ArrayList<>();
                Payment[] arr = new Payment[3];
                String prevStreamId = "";
                int arr_i = 0;
                for (int i = 0; i < events.size(); i++) {
                    if (events.get(i).getStreamId().equals(prevStreamId)) {
                        Payment payment = events.get(i).getData();
                        arr[arr_i++] = payment;
                    } else {
                        if (i > 0) groupedPayments.add(arr);
                        arr = new Payment[3];
                        arr_i = 0;
                        prevStreamId = events.get(i).getStreamId();
                        arr[arr_i++] = events.get(i).getData();
                    }
                }
                return groupedPayments;
            }, exec);
        }
    }
}
