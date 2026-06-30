package com.hotel.backend.controller;
 
import com.hotel.backend.dto.request.CreateReviewRequest;
import com.hotel.backend.dto.request.UpdateReviewRequest;
import com.hotel.backend.dto.response.ApiResponse;
import com.hotel.backend.dto.response.ReviewResponse;
import com.hotel.backend.dto.response.RoomTypeRatingResponse;
import com.hotel.backend.entity.User;
import com.hotel.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
 
    private final ReviewService reviewService;
 
    // ── Public: xem review theo room type ────────────────────────────────────
    @GetMapping("/room-type/{roomTypeId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByRoomType(@PathVariable Long roomTypeId) {
        return ApiResponse.success(reviewService.getReviewsByRoomType(roomTypeId));
    }
 
    // ── Public: điểm trung bình + tổng số review ─────────────────────────────
    @GetMapping("/room-type/rating/{roomTypeId}")
    public ApiResponse<RoomTypeRatingResponse> getRoomTypeRating(@PathVariable Long roomTypeId) {
        return ApiResponse.success(reviewService.getRoomTypeRating(roomTypeId));
    }
 
    // ── Customer: tạo review ──────────────────────────────────────────────────
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
        Long userId = getCurrentUserId();
        return ApiResponse.success("Đánh giá thành công",
                reviewService.createReview(userId, request));
    }
 
    // ── Customer: xem review của mình ────────────────────────────────────────
    @GetMapping("/my")
    public ApiResponse<List<ReviewResponse>> getMyReviews() {
        return ApiResponse.success(reviewService.getReviewsByUser(getCurrentUserId()));
    }
 
    // ── Customer: sửa review của mình ────────────────────────────────────────
    @PatchMapping("/{id}")
    public ApiResponse<ReviewResponse> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReviewRequest request) {
        return ApiResponse.success("Cập nhật đánh giá thành công",
                reviewService.updateReview(getCurrentUserId(), id, request));
    }
 
    // ── Customer/Admin: xóa review ────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReview(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        boolean isAdmin = currentUser.getType().name().equals("ADMIN");
        reviewService.deleteReview(currentUser.getId(), id, isAdmin);
        return ApiResponse.success("Xóa đánh giá thành công", null);
    }
 
    // ── Helpers ────────────────────────────────────────────────────────────────
    private User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
 
    private Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}