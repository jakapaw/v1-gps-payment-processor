package dev.jakapaw.giftcard.paymentmanager.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentId implements Serializable {

    private Long paymentNumber;
    private Integer version;
}
