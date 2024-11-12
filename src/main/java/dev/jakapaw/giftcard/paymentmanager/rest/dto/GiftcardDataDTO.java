package dev.jakapaw.giftcard.paymentmanager.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GiftcardDataDTO {

    private String serialNumber;
    private String seriesId;
    private double balance;
}
