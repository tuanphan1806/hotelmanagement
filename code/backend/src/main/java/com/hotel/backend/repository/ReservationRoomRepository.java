package com.hotel.backend.repository;

import com.hotel.backend.constant.AssignStatus;
import com.hotel.backend.entity.ReservationRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRoomRepository extends JpaRepository<ReservationRoom, Long> {

    List<ReservationRoom> findByReservationRoomTypeId(Long reservationRoomTypeId);

    List<ReservationRoom> findByStatus(AssignStatus status);

    // Lấy tất cả ReservationRoom của 1 reservation (qua reservationRoomType)
    @Query("""
        SELECT rr FROM ReservationRoom rr
        JOIN rr.reservationRoomType rrt
        WHERE rrt.reservation.id = :reservationId
    """)
    List<ReservationRoom> findAllByReservationId(@Param("reservationId") Long reservationId);
     boolean existsByRoomIdAndReservationRoomTypeReservationId(Long roomId, Long reservationId);
    // Kiểm tra phòng cụ thể đã được assign cho reservation room chưa
    boolean existsByRoomIdAndStatus(Long roomId, AssignStatus status);

    @Query("""
        SELECT CASE WHEN COUNT(rr) > 0 THEN true ELSE false END
        FROM ReservationRoom rr
        JOIN rr.reservationRoomType rrt
        JOIN rrt.reservation r
        WHERE rr.room.id = :roomId
          AND r.id <> :reservationId
          AND rr.status IN :blockingStatuses
          AND r.status NOT IN (
              com.hotel.backend.constant.ReservationStatus.CANCELLED,
              com.hotel.backend.constant.ReservationStatus.CHECKED_OUT
          )
          AND r.checkIn < :checkOut
          AND r.checkOut > :checkIn
    """)
    boolean existsOverlappingRoomAssignment(
            @Param("roomId") Long roomId,
            @Param("reservationId") Long reservationId,
            @Param("checkIn") java.time.LocalDateTime checkIn,
            @Param("checkOut") java.time.LocalDateTime checkOut,
            @Param("blockingStatuses") List<AssignStatus> blockingStatuses);
}
