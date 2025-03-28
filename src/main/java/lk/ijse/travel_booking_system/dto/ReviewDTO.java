package lk.ijse.travel_booking_system.dto;

public class ReviewDTO {
    private Long reviewId;
    private int rating;
    private String comment;
    private String createdAt;
    private Long userId;
    private Long tPackageId;

    public ReviewDTO() {
    }

    public ReviewDTO(Long reviewId, int rating, String comment, String createdAt, Long userId, Long tPackageId) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.userId = userId;
        this.tPackageId = tPackageId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
        return "ReviewDTO{" +
                "reviewId=" + reviewId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", userId=" + userId +
                ", tPackageId=" + tPackageId +
                '}';
    }
}
