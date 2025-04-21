package lk.ijse.travel_booking_system.Security.Responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponse {
    private String tokens;
    private String role;
}
