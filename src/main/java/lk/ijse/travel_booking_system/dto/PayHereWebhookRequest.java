package lk.ijse.travel_booking_system.dto;

import lombok.Data;

@Data
public class PayHereWebhookRequest {
    private String merchant_id;
    private String order_id;
    private String payment_id;
    private String payhere_amount;
    private String payhere_currency;
    private String status_code;
    private String md5sig;
    private String custom_1;
    private String custom_2;
}