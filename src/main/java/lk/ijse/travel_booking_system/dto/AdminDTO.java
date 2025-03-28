package lk.ijse.travel_booking_system.dto;

public class AdminDTO {
    private Long adminId;
    private String name;
    private String email;

    public AdminDTO() {
    }

    public AdminDTO(Long adminId, String name, String email) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "adminId=" + adminId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}