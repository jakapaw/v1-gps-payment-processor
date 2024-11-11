package dev.jakapaw.giftcard.paymentmanager.common;

import lombok.Getter;

import java.util.EventObject;

/**
 * Wrapper for payment events. This class is used as a solution for object to JSON serialization error
 * when each event inherit EventObject
 * @param <T> any Event
 */

@Getter
public class PaymentEventWrapper<T> extends EventObject {

    T event;

    public PaymentEventWrapper(Object source, T event) {
        super(source);
        this.event = event;
    }
}