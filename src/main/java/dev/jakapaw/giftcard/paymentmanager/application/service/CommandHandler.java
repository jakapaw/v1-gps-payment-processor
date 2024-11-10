package dev.jakapaw.giftcard.paymentmanager.application.service;

import dev.jakapaw.giftcard.paymentmanager.infrastructure.broker.KafkaProducer;
import org.springframework.stereotype.Service;

@Service
public class CommandHandler {

    KafkaProducer kafkaProducer;

    public CommandHandler(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
}
