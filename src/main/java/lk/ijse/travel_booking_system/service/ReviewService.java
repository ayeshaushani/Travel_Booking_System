package lk.ijse.travel_booking_system.service;

import lk.ijse.travel_booking_system.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    ReviewDTO saveReview(ReviewDTO reviewDTO);
    List<ReviewDTO> getAllReviews();
    ReviewDTO getReviewById(Long id);
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    void deleteReview(Long id);
}
