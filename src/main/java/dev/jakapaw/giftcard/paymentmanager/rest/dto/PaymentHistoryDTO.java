package dev.jakapaw.giftcard.paymentmanager.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class PaymentHistoryDTO {

    private final String giftcardSerialNumber;

    @Setter
    private List<PaymentDetailDTO[]> paymentHistory;

    public PaymentHistoryDTO(String giftcardSerialNumber) {
        this.giftcardSerialNumber = giftcardSerialNumber;
    }
}
