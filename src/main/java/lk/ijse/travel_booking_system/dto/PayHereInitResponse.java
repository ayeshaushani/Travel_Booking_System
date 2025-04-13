package lk.ijse.travel_booking_system.dto;

import lombok.Data;

@Data
public class PayHereInitResponse {
    private String merchantId;
    private String orderId;
    private Double amount;
    private String currency;
    private String items;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String returnUrl;
    private String cancelUrl;
    private String notifyUrl;
    private Boolean sandbox;
}
