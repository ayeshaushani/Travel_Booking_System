package lk.ijse.travel_booking_system.dto;

import jakarta.validation.constraints.*;

public class GuideDTO {
    private Long guideId;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Contact is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid contact number")
    private String contact;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 0, message = "Experience cannot be negative")
    private int experience;

    public GuideDTO(Long guideId, String name, String contact, int experience , String email) {
        this.guideId = guideId;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.experience = experience;
    }

    public GuideDTO() {

    }

    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "GuideDTO{" +
                "guideId=" + guideId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ",email='" + email + '\''+
                ", experience=" + experience +
                '}';
    }
}
