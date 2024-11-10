package dev.jakapaw.giftcard.paymentmanager.common;

import lombok.Getter;

import java.util.EventObject;

@Getter
public class PaymentEventWrapper<T> extends EventObject {

    T event;

    public PaymentEventWrapper(Object source, T event) {
        super(source);
        this.event = event;
    }
}