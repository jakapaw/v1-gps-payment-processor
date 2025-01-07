package dev.jakapaw.giftcard.paymentmanager.common;

import java.util.EventObject;

import lombok.Getter;

/**
 * Wrapper for payment events. This class is used as a solution for object to JSON serialization error
 * when each event inherit EventObject
 * @param <T> any Event
 */

@Getter
public class PaymentEvent<T> extends EventObject {

    T event;

    public PaymentEvent(Object source, T event) {
        super(source);
        this.event = event;
    }
}