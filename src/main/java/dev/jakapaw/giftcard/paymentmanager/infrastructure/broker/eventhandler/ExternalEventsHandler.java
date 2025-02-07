package dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.eventhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.jakapaw.giftcard.paymentmanager.application.service.PaymentService;

@Component
public class ExternalEventsHandler {

    PaymentService paymentService;
    
    @Autowired
    ObjectMapper om;

    public ExternalEventsHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(id = "payment-process", topics = "payment.process.end")
    public void listen(String message) throws JsonMappingException, JsonProcessingException {
        SharedPaymentEvent event = om.readValue(message, SharedPaymentEvent.class);
        if (event.getPaymentStatus().compareTo(SharedPaymentEvent.PaymentStatus.ACCEPTED.name()) == 0) {
            paymentService.completePayment(event.getPaymentId());
        } else {
            paymentService.declinePayment(event.getPaymentId());
        }
    }
}
