package lk.ijse.travel_booking_system.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PayHereInitRequest {
    @NotNull
    private Long bookingId;
    
    @NotBlank
    private String returnUrl;
    
    @NotBlank
    private String cancelUrl;
}