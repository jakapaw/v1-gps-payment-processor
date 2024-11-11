package dev.jakapaw.giftcard.paymentmanager.infrastructure.repository;

import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface PaymentEventDatastore extends JpaRepository<PaymentEvent, String> {
}
