package dev.jakapaw.giftcard.paymentmanager.infrastructure.webhook;

import org.springframework.stereotype.Component;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;

@Component
public class PaymentWebhook {
    
    // RestClient restClient;

    // public PaymentWebhook(RestClient restClient) {
    //     this.restClient = restClient;
    // }

    public void notifyPaymentDeclined(Payment payment) {
        // for prototype project, we just save finished payment into a database
        // and record the time
        // System.out.println(LocalDateTime.now());
    }

    public void notifyPaymentAccepted(Payment payment) {
        // for prototype project, we just save finished payment into a database
        // and record the time
        // System.out.println(LocalDateTime.now());
    }

    public void notifyPaymentError() {
        // System.out.println(LocalDateTime.now());
    }
}
