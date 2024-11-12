package dev.jakapaw.giftcard.paymentmanager.infrastructure.repository;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentEventDatastore extends JpaRepository<PaymentEvent, String> {

    List<PaymentEvent> getPaymentEventsByGiftcardSerialNumber(String giftcardSerialNumber);
}
