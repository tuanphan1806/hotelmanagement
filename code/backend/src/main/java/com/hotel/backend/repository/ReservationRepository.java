package com.hotel.backend.repository;

import com.hotel.backend.constant.ReservationStatus;
import com.hotel.backend.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByReservationCode(String reservationCode);

    boolean existsByReservationCode(String reservationCode);

    @Query("""
    SELECT DISTINCT r FROM Reservation r
    LEFT JOIN FETCH r.roomTypes rt
    LEFT JOIN FETCH rt.roomType
    LEFT JOIN FETCH rt.roomHold
    WHERE r.customer.id = :customerId
    ORDER BY r.createdAt DESC
    """)
    List<Reservation> findByCustomerIdOrderByCreatedAtDesc(@Param("customerId") Long customerId);

    List<Reservation> findByStatus(ReservationStatus status);

    @Query("""
        SELECT r FROM Reservation r
        JOIN FETCH r.customer
        JOIN FETCH r.roomTypes rt
        JOIN FETCH rt.roomType
        LEFT JOIN FETCH rt.roomHold
        WHERE r.id = :id
    """)
    Optional<Reservation> findByIdWithDetails(@Param("id") Long id);

        // Lấy tất cả (staff/admin)
    @Query("""
        SELECT DISTINCT r FROM Reservation r
        JOIN FETCH r.customer
        JOIN FETCH r.roomTypes rt
        JOIN FETCH rt.roomType
        LEFT JOIN FETCH rt.roomHold
        ORDER BY r.createdAt DESC
    """)
    List<Reservation> findAllWithDetails();
}