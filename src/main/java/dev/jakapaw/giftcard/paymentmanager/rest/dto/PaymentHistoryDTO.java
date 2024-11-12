package dev.jakapaw.giftcard.paymentmanager.rest.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PaymentHistoryDTO {

    private final String giftcardSerialNumber;
    private List<PaymentDetailDTO> paymentHistory;

    public PaymentHistoryDTO(String giftcardSerialNumber) {
        this.giftcardSerialNumber = giftcardSerialNumber;
    }
}
