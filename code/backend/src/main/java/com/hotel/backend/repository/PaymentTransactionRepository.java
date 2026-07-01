package com.hotel.backend.repository;

import com.hotel.backend.entity.PaymentTransaction;
import com.hotel.backend.constant.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, String> {

    Optional<PaymentTransaction> findByTxnRef(String txnRef);
    Optional<PaymentTransaction> findByProviderTxnId(String providerTxnId);
    
    @Query("SELECT pt FROM PaymentTransaction pt WHERE pt.reservation.id = :reservationId")
    List<PaymentTransaction> findByReservationId(@Param("reservationId") Long reservationId);
 
    @Query("""
        SELECT pt FROM PaymentTransaction pt
        WHERE pt.reservation.id = :reservationId
        AND pt.status = :status
    """)
    List<PaymentTransaction> findByReservationIdAndStatus(
            @Param("reservationId") Long reservationId,
            @Param("status") PaymentStatus status);
 
    @Query("""
        SELECT CASE WHEN COUNT(pt) > 0 THEN true ELSE false END
        FROM PaymentTransaction pt
        WHERE pt.reservation.id = :reservationId
        AND pt.status = :status
    """)
    boolean existsByReservationIdAndStatus(
            @Param("reservationId") Long reservationId,
            @Param("status") PaymentStatus status);

    @Query("""
        SELECT CASE WHEN COUNT(pt) > 0 THEN true ELSE false END
        FROM PaymentTransaction pt
        WHERE pt.reservation.id = :reservationId
        AND pt.status IN :statuses
    """)
    boolean existsByReservationIdAndStatusIn(
            @Param("reservationId") Long reservationId,
            @Param("statuses") List<PaymentStatus> statuses);


     @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM PaymentTransaction p
        WHERE p.reservation.id = :reservationId
          AND p.status = com.hotel.backend.constant.PaymentStatus.SUCCESS
    """)
    Long sumSuccessAmountByReservationId(@Param("reservationId") Long reservationId);


     @Query("""
        SELECT CASE
                 WHEN COALESCE(SUM(p.amount),0) >= :requiredDeposit
                 THEN true
                 ELSE false
               END
        FROM PaymentTransaction p
        WHERE p.reservation.id = :reservationId
          AND p.status = com.hotel.backend.constant.PaymentStatus.SUCCESS
    """)
    boolean hasPaidEnough(
            @Param("reservationId") Long reservationId,
            @Param("requiredDeposit") Long requiredDeposit);


     @Query("""
        SELECT CASE
                 WHEN COALESCE(SUM(p.amount),0) >= r.totalAmount
                 THEN true
                 ELSE false
               END
        FROM Reservation r
        LEFT JOIN PaymentTransaction p
               ON p.reservation.id = r.id
              AND p.status = com.hotel.backend.constant.PaymentStatus.SUCCESS
        WHERE r.id = :reservationId
        GROUP BY r.totalAmount
    """)
    boolean isFullyPaid(@Param("reservationId") Long reservationId);

}
