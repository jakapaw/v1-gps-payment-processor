package dev.jakapaw.giftcard.paymentmanager.application.query;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

@Getter
public class GetPaymentHistoryQuery extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 4022317978933362400L;
    private String giftcardSerialNumber;

    public GetPaymentHistoryQuery(Object source, String giftcardSerialNumber) {
        super(source);
        this.giftcardSerialNumber = giftcardSerialNumber;
    }
}
