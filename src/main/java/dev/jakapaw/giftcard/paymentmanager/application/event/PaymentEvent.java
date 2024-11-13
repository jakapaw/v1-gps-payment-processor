package dev.jakapaw.giftcard.paymentmanager.application.event;

import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
public class PaymentEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    private String streamId;
    private Integer version;

    @Getter
    @JdbcTypeCode(SqlTypes.JSON)
    private Payment data;
    private String giftcardSerialNumber;
    private LocalDateTime createdTime;

    public PaymentEvent(String streamId, Integer version, Payment data, String giftcardSerialNumber) {
        this.streamId = streamId;
        this.version = version;
        this.data = data;
        this.giftcardSerialNumber = giftcardSerialNumber;
        this.createdTime = data.getPaymentTime();
    }

    public void updateData(Payment data) {
        this.data = data;
        version++;
    }
}
