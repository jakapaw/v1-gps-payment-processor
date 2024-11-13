package dev.jakapaw.giftcard.paymentmanager.infrastructure.repository;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentEventDatastore extends JpaRepository<PaymentEvent, String> {

    List<PaymentEvent> getPaymentEventsByGiftcardSerialNumber(String giftcardSerialNumber);

    @Query("select pe from PaymentEvent pe where pe.giftcardSerialNumber = ?1 " +
            "and pe.createdTime > ?2 and pe.createdTime < ?3")
    List<PaymentEvent> getPaymentEventsAtTimeRange(
            String giftcardSerialNumber, LocalDateTime startTime, LocalDateTime endTime);
}
