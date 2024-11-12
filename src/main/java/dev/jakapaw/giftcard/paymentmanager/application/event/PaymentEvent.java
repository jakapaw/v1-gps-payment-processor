package dev.jakapaw.giftcard.paymentmanager.application.event;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class PaymentEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String streamId;
    private Integer version;
    @JdbcTypeCode(SqlTypes.JSON)
    private Payment data;
    private String giftcardSerialNumber;

    public PaymentEvent(String streamId, Integer version, Payment data, String giftcardSerialNumber) {
        this.streamId = streamId;
        this.version = version;
        this.data = data;
        this.giftcardSerialNumber = giftcardSerialNumber;
    }

    public void updateData(Payment data) {
        this.data = data;
        version++;
    }
}
