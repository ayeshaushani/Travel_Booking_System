package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.PaymentDTO;
import java.util.List;

public interface PaymentService {
    PaymentDTO savePayment(PaymentDTO paymentDTO);
    List<PaymentDTO> getAllPayments();
    PaymentDTO getPaymentById(Long id);
    PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO);
    void deletePayment(Long id);
}
