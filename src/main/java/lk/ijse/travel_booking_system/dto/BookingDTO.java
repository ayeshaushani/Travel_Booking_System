package lk.ijse.travel_booking_system.dto;

public class BookingDTO {
    private Long bookingId;
    private String bookingDate;
    private String status;
    private Long userId;
    private Long tPackageId;
    private String userEmail;
    private int numberOfTravelers;
    private String specialRequests;
    public BookingDTO() {
    }

    public BookingDTO(Long bookingId, String bookingDate, String status, Long userId, Long tPackageId, String userEmail, int numberOfTravelers, String specialRequests) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.userId = userId;
        this.tPackageId = tPackageId;
        this.userEmail = userEmail;
        this.numberOfTravelers = numberOfTravelers;
        this.specialRequests = specialRequests;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long gettPackageId() {
        return tPackageId;
    }

    public void settPackageId(Long tPackageId) {
        this.tPackageId = tPackageId;
    }

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

    public int getNumberOfTravelers() {return numberOfTravelers;}

    public void setNumberOfTravelers(int numberOfTravelers) {this.numberOfTravelers = numberOfTravelers;}

    public String getSpecialRequests() {return specialRequests;}

    public void setSpecialRequests(String specialRequests) {this.specialRequests = specialRequests;}

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", bookingDate='" + bookingDate + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", tPackageId=" + tPackageId +
                ", userEmail='" + userEmail + '\'' +
                ", numberOfTravelers=" + numberOfTravelers +
                ", specialRequests='" + specialRequests + '\'' +
                '}';
    }
}