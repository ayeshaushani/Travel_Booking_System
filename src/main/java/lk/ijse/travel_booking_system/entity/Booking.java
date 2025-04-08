package lk.ijse.travel_booking_system.entity;

import jakarta.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private String bookingDate;
    private String status; // Pending, Confirmed, Canceled
    @Column(name = "user_email")
    private String userEmail;  // New field for user email

    @Column(name = "number_of_travelers")
    private int numberOfTravelers;  // New field for number of travelers

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

    public Booking(Long bookingId, String bookingDate, String status, User user, TravelPackage travelPackage, Payment payment, String specialRequests, String userEmail, String numberOfTravelers) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.user = user;
        this.travelPackage = travelPackage;
        this.payment = payment;
        this.userEmail = user.getEmail();
        this.numberOfTravelers = getNumberOfTravelers();
        this.specialRequests = getSpecialRequests();
    }

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
