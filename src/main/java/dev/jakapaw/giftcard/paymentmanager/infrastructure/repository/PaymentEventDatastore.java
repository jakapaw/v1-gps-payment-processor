package dev.jakapaw.giftcard.paymentmanager.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.domain.PaymentId;

public interface PaymentEventDatastore extends JpaRepository<Payment, PaymentId> {
    
    List<Payment> findByGiftcard(String giftcard);
    List<Payment> findByGiftcardAndUpdateTimeBetween(String giftcard, LocalDateTime start, LocalDateTime end);
}
