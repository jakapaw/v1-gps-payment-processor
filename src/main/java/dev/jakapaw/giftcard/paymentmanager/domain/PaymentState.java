package dev.jakapaw.giftcard.paymentmanager.domain;

public enum PaymentState {
    PAYMENT_INITIALIZED,
    PAYMENT_ON_PROCESS,
    PAYMENT_ON_VERIFICATION,
    PAYMENT_COMPLETED,
    PAYMENT_DECLINED
}
