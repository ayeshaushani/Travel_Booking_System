package lk.ijse.travel_booking_system.dto;

import lombok.Builder;

@Builder

public class UserDTO {
    private Long userId;
    private String email;
    private String role;
    private String password;

    public UserDTO(Long userId, String email, String role, String password) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public UserDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}