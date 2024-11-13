package dev.jakapaw.giftcard.paymentmanager.application.query;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class GetGiftcardStateQuery extends ApplicationEvent {

    private final String giftcardSerialNumber;
    private final double balance;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public GetGiftcardStateQuery(
            Object source, String giftcardSerialNumber, double balance, LocalDateTime startTime, LocalDateTime endTime
    ) {
        super(source);
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.balance = balance;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
