package dev.jakapaw.giftcard.paymentmanager.rest;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.service.CommandHandler;
import dev.jakapaw.giftcard.paymentmanager.application.service.PaymentService;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.InitiatePaymentDTO;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.PaymentDetailDTO;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.PaymentHistoryDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/payment")
public class MainController {

    PaymentService paymentService;

    public MainController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public PaymentDetailDTO initiatePayment(@RequestBody InitiatePaymentDTO data) throws ExecutionException, InterruptedException {
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

    @GetMapping("/history")
    public PaymentHistoryDTO paymentHistory(@RequestParam String giftcard) {
        paymentService.getPaymentHistory(giftcard);
    }
}
