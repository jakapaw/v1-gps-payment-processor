package dev.jakapaw.giftcard.paymentmanager.rest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.jakapaw.giftcard.paymentmanager.application.command.InitiatePaymentCommand;
import dev.jakapaw.giftcard.paymentmanager.application.query.GetGiftcardStateQuery;
import dev.jakapaw.giftcard.paymentmanager.application.service.PaymentService;
import dev.jakapaw.giftcard.paymentmanager.domain.Payment;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.GiftcardQueryDTO;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.InitiatePaymentDTO;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.PaymentDetailDTO;
import dev.jakapaw.giftcard.paymentmanager.rest.dto.PaymentHistoryDTO;

@RestController
@RequestMapping("/api/payment")
public class MainController {

    PaymentService paymentService;

    public MainController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public PaymentDetailDTO initiatePayment(@RequestBody InitiatePaymentDTO body) throws ExecutionException, InterruptedException {
        InitiatePaymentCommand command = new InitiatePaymentCommand(
                body.getGiftcardSerialNumber(), body.getMerchantId(), body.getBilled()
        );
        Payment paymentResult = paymentService.initiatePayment(command).get();

        return new PaymentDetailDTO(
                paymentResult.getPaymentId(),
                paymentResult.getGiftcard(),
                paymentResult.getMerchantId(),
                paymentResult.getBillAmount(),
                paymentResult.getPaymentTime(),
                paymentResult.getPaymentStatus());
    }

    @GetMapping("/history")
    public PaymentHistoryDTO paymentHistory(@RequestParam String giftcard) {
        PaymentHistoryDTO resBody = new PaymentHistoryDTO(giftcard);

        List<PaymentDetailDTO[]> paymentHistory = paymentService.getPaymentHistory(giftcard).thenApply(payments -> {
            var futureResult = payments.stream().map(p -> {
                PaymentDetailDTO[] newArr = new PaymentDetailDTO[3];
                for (int i = 0; i < p.length; i++) {
                    PaymentDetailDTO dto = new PaymentDetailDTO(
                            p[i].getPaymentId(),
                            p[i].getGiftcard(),
                            p[i].getMerchantId(),
                            p[i].getBillAmount(),
                            p[i].getPaymentTime(),
                            p[i].getPaymentStatus()
                    );
                    newArr[i] = dto;
                }
                return newArr;
            }).toList();
            return futureResult;
        }).join();

        resBody.setPaymentHistory(paymentHistory);
        return resBody;
    }

    @PostMapping("/balance")
    public GiftcardQueryDTO giftcardBalance(@RequestBody GiftcardQueryDTO body) {
        GetGiftcardStateQuery query = new GetGiftcardStateQuery(
                this,
                body.getGiftcardSerialNumber(),
                body.getBalance(),
                body.getStartTime(),
                body.getEndTime()
        );
        double finalBalance = paymentService.getGiftcardStateAtTime(query).join();
        body.setBalance(finalBalance);
        return body;
    }
}
