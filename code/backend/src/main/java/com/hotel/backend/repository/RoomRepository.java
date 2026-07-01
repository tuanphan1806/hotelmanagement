package com.hotel.backend.repository;

import com.hotel.backend.constant.RoomStatus;
import com.hotel.backend.constant.CleaningStatus;
import com.hotel.backend.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

       boolean existsByRoomName(String roomName);       
       Optional<Room> findByRoomName(String roomName);
       List<Room> findByStatus(RoomStatus status);      
       List<Room> findByRoomTypeId(Long roomTypeId);    

       @Lock(LockModeType.PESSIMISTIC_WRITE)
       @Query("SELECT r FROM Room r WHERE r.id = :id")
       Optional<Room> findByIdForUpdate(@Param("id") Long id);

       @Query("SELECT r FROM Room r JOIN r.roomType rt WHERE " +
              "(:keyword IS NULL OR LOWER(r.roomName) LIKE CONCAT('%', LOWER(:keyword), '%') " +
              " OR LOWER(rt.typeName) LIKE CONCAT('%', LOWER(:keyword), '%'))"+
              "AND (:status IS NULL OR r.status = :status) " +
              "AND (:cleaningStatus IS NULL OR r.cleaningStatus = :cleaningStatus)")
       List<Room> search(@Param("keyword") String keyword,
                         @Param("status") RoomStatus status,
                         @Param("cleaningStatus") CleaningStatus cleaningStatus);   


       @Query("SELECT r FROM Room r JOIN r.roomType rt WHERE LOWER(r.roomName) LIKE :keyword OR LOWER(rt.typeName) LIKE :keyword OR LOWER(r.status) LIKE :keyword or LOWER(r.cleaningStatus) LIKE :keyword")
       Page<Room> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
       
}
