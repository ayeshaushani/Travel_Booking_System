package lk.ijse.travel_booking_system.entity;

import jakarta.persistence.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private String bookingDate;
    private String status; // Pending, Confirmed, Canceled

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tPackageId")
    private TravelPackage travelPackage;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;
    public Booking() {}

    public Booking(Long bookingId, String bookingDate, String status, User user, TravelPackage travelPackage, Payment payment) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.user = user;
        this.travelPackage = travelPackage;
        this.payment = payment;
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

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingDate='" + bookingDate + '\'' +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", travelPackage=" + travelPackage +
                ", payment=" + payment +
                '}';
    }
}
