package lk.ijse.travel_booking_system.dto;

public class BookingDTO {
    private Long bookingId;
    private String bookingDate;
    private String status;
    private Long userId;
    private Long tPackageId;


    public BookingDTO() {
    }

    public BookingDTO(Long bookingId, String bookingDate, String status, Long userId, Long tPackageId) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.status = status;
        this.userId = userId;
        this.tPackageId = tPackageId;
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

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", bookingDate='" + bookingDate + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", tPackageId=" + tPackageId +
                '}';
    }
}