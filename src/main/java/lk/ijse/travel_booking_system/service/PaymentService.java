package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.PayHereWebhookRequest;
import lk.ijse.travel_booking_system.dto.PaymentDTO;
import lk.ijse.travel_booking_system.entity.Payment;

import java.util.List;

public interface PaymentService {
    PaymentDTO savePayment(PaymentDTO paymentDTO);
    List<Payment> getAllPayments();
    PaymentDTO getPaymentById(Long id);
    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);
    void deletePayment(Long id);
/*
    String getPayHereMerchantId();

    String getPayHereNotifyUrl();

    boolean verifyPayHereSignature(PayHereWebhookRequest request, String receivedSignature);

    void processPayHerePayment(String orderId, String paymentId,
                               String statusCode, String amount, String signature);*/
}
