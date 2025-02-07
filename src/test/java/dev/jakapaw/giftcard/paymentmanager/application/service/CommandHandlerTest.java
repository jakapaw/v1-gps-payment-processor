package dev.jakapaw.giftcard.paymentmanager.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.event.PaymentInitiated;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CommandHandlerTest {

    @Autowired
    CommandHandler commandHandler;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    // event
    BlockingDeque<Object> queue = new LinkedBlockingDeque<>();

    @Test
    void givenInitiatePaymentCommand_WhenInitiatePayment_ThenPublishPaymentInitiatedEvent() {
        // given
        InitiatePaymentCommand command = new InitiatePaymentCommand("0100-0000-0000-1000", "RTVW", 10);
        // when
        commandHandler.initiatePayment(command);
        // then
        assertEquals(InitiatePaymentCommand.class, queue.pop().getClass());
    }

    @EventListener
    public void on(PaymentInitiated event) {
        queue.add(event);
    }
}
