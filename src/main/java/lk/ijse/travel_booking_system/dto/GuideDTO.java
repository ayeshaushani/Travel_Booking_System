package lk.ijse.travel_booking_system.dto;

public class GuideDTO {
    private Long guideId;
    private String name;
    private String contact;
    private int experience;

    public GuideDTO(Long guideId, String name, String contact, int experience) {
        this.guideId = guideId;
        this.name = name;
        this.contact = contact;
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

    @Override
    public String toString() {
        return "GuideDTO{" +
                "guideId=" + guideId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", experience=" + experience +
                '}';
    }
}
