package lk.ijse.travel_booking_system.service.impl;

import lk.ijse.travel_booking_system.dto.ReviewDTO;
import lk.ijse.travel_booking_system.entity.Review;
import lk.ijse.travel_booking_system.entity.User;
import lk.ijse.travel_booking_system.entity.TravelPackage;
import lk.ijse.travel_booking_system.repo.ReviewRepository;
import lk.ijse.travel_booking_system.repo.UserRepository;
import lk.ijse.travel_booking_system.repo.TravelPackageRepository;
import lk.ijse.travel_booking_system.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TravelPackageRepository travelPackageRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, TravelPackageRepository travelPackageRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.travelPackageRepository = travelPackageRepository;
    }

    @Override
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelPackage travelPackage = travelPackageRepository.findById(reviewDTO.gettPackageId())
                .orElseThrow(() -> new RuntimeException("Travel Package not found"));

        Review review = new Review(
                null, // Auto-generated ID
                reviewDTO.getRating(),
                reviewDTO.getComment(),
                reviewDTO.getCreatedAt(),
                user,
                travelPackage
        );

        Review savedReview = reviewRepository.save(review);
        return mapToDTO(savedReview);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return mapToDTO(review);
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TravelPackage travelPackage = travelPackageRepository.findById(reviewDTO.gettPackageId())
                .orElseThrow(() -> new RuntimeException("Travel Package not found"));

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setCreatedAt(reviewDTO.getCreatedAt());
        review.setUser(user);
        review.setTravelPackage(travelPackage);

        Review updatedReview = reviewRepository.save(review);
        return mapToDTO(updatedReview);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO mapToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUser().getUserId(),
                review.getTravelPackage().gettPackageId()
        );
    }
}
