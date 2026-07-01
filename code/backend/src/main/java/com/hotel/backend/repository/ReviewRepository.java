package com.hotel.backend.repository;

import com.hotel.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByUserIdAndReservationId(Long userId, Long reservationId);

    Optional<Review> findByUserIdAndReservationId(Long userId, Long reservationId);

    boolean existsByUserIdAndReservationIdAndRoomTypeId(Long userId, Long reservationId, Long roomTypeId);

    Optional<Review> findByUserIdAndReservationIdAndRoomTypeId(Long userId, Long reservationId, Long roomTypeId);

    List<Review> findByRoomTypeIdOrderByCreatedAtDesc(Long roomTypeId);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT COALESCE(AVG(r.rating), 0.0) FROM Review r WHERE r.roomType.id = :roomTypeId")
    Double getAverageRatingByRoomType(@Param("roomTypeId") Long roomTypeId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.roomType.id = :roomTypeId")
    long countByRoomTypeId(@Param("roomTypeId") Long roomTypeId);

    @Query("""
        SELECT r FROM Review r
        JOIN FETCH r.user
        JOIN FETCH r.roomType
        WHERE r.roomType.id = :roomTypeId
        ORDER BY r.createdAt DESC
    """)
    List<Review> findByRoomTypeIdWithDetails(@Param("roomTypeId") Long roomTypeId);
}
