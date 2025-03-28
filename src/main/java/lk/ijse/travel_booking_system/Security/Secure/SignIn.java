package lk.ijse.travel_booking_system.Security.Secure;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignIn {
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;
    @NotNull(message = "password cannot be null")
    private String password;
}
