package dev.jakapaw.giftcard.paymentmanager.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PaymentId implements Serializable {

    private String paymentId;
    private Integer version;
}
