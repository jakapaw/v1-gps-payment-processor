package dev.jakapaw.giftcard.paymentmanager.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class GiftcardQueryDTO {

    private String giftcardSerialNumber;
    @Setter
    private double balance;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
