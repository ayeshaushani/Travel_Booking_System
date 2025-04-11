package lk.ijse.travel_booking_system.entity;

import jakarta.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private String bookingDate;
    private String status;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "number_of_travelers")
    private int numberOfTravelers;

    @Column(name = "special_requests")
    private String specialRequests;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tPackageId")
    private TravelPackage travelPackage;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    public Booking() {}

    public Booking(Long bookingId, String bookingDate, String status, int numberOfTravelers, String userEmail,
                   String specialRequests, User user, TravelPackage travelPackage, Payment payment) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.numberOfTravelers = numberOfTravelers;
        this.userEmail = userEmail;
        this.specialRequests = specialRequests;
        this.user = user;
        this.travelPackage = travelPackage;
        this.payment = payment;
    }

    // Getters and Setters

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(int numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingDate='" + bookingDate + '\'' +
                ", status='" + status + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", numberOfTravelers=" + numberOfTravelers +
                ", specialRequests='" + specialRequests + '\'' +
                ", user=" + user +
                ", travelPackage=" + travelPackage +
                ", payment=" + payment +
                '}';
    }
}
