package dev.jakapaw.giftcard.paymentmanager.rest;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.service.PaymentService;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.InitiatePayment;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.PaymentDetailDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/payment")
public class MainController {

    PaymentService paymentService;

    public MainController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public PaymentDetailDTO initiatePayment(@RequestBody InitiatePayment data) throws ExecutionException, InterruptedException {
        InitiatePaymentCommand command = new InitiatePaymentCommand(
                data.getGiftcardSerialNumber(), data.getMerchantId(), data.getBilled()
        );
        Payment paymentResult = paymentService.initiatePayment(command).get();

        return new PaymentDetailDTO(
                paymentResult.getPaymentId(),
                paymentResult.getGiftcardSerialNumber(),
                paymentResult.getMerchantId(),
                paymentResult.getBillAmount(),
                paymentResult.getPaymentTime(),
                paymentResult.getPaymentStatus());
    }
}
