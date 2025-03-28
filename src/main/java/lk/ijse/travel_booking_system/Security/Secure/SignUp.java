package lk.ijse.travel_booking_system.Security.Secure;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignUp {
    @NotNull(message = "email cannot be null")
    private String email;
    @NotNull(message = "weak password")
    private String password;
    @NotNull(message = "select your role")
    private String role;

}
