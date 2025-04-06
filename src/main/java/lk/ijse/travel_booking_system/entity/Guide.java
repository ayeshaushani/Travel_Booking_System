package lk.ijse.travel_booking_system.entity;

import jakarta.persistence.*;

@Entity
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guideId;
    private String name;
    private String contact;
    private String email;
    private int experience;

    @ManyToOne
    @JoinColumn(name = "tPackageId")
    private TravelPackage travelPackage;

    public Guide() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Guide(Long guideId, String name, String contact, int experience, TravelPackage travelPackage , String email) {
        this.guideId = guideId;
        this.name = name;
        this.contact = contact;
        this.experience = experience;
        this.travelPackage = travelPackage;
        this.email = email;
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

    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "guideId=" + guideId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", experience=" + experience +
                ", travelPackage=" + travelPackage +
                '}';
    }
}