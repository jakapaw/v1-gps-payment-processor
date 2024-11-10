package dev.jakapaw.giftcard.paymentmanager.api.rest;

import dev.jakapaw.giftcard.paymentmanager.api.dto.InitiatePayment;
import dev.jakapaw.giftcard.paymentmanager.api.dto.PaymentDetail;
import dev.jakapaw.giftcard.paymentmanager.application.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class MainController {

    PaymentService paymentService;

    public MainController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public PaymentDetail initiatePayment(@RequestBody InitiatePayment data) {
        String status = paymentService.startPayment(data.getGiftcardId(), data.getMerchantId(), data.getBilled());
        return new PaymentDetail(
                data.getGiftcardId(),
                data.getMerchantId(),
                data.getBilled(),
                status
        );
    }
}
