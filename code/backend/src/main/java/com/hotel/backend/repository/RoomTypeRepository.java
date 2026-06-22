package com.hotel.backend.repository;

import com.hotel.backend.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    /**
     * Lấy tất cả room types kèm facilities.
     * JOIN FETCH + DISTINCT tránh N+1 và duplicate rows khi một room type có nhiều facility.
     */
    @Query("SELECT DISTINCT rt FROM RoomType rt LEFT JOIN FETCH rt.facilities ORDER BY rt.id ASC")
    List<RoomType> findAllWithFacilities();

    /**
     * Lấy một room type kèm facilities theo ID.
     */
    @Query("SELECT rt FROM RoomType rt LEFT JOIN FETCH rt.facilities WHERE rt.id = :id")
    Optional<RoomType> findByIdWithFacilities(@Param("id") Long id);

    /**
     * Lọc theo khoảng giá — dùng cho API filter.
     */
    List<RoomType> findByPriceBetweenOrderByPriceAsc(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Kiểm tra trùng tên khi tạo mới.
     */
    boolean existsByTypeNameIgnoreCase(String typeName);

    /**
     * Kiểm tra trùng tên khi cập nhật (loại trừ chính bản ghi đang sửa).
     */
    boolean existsByTypeNameIgnoreCaseAndIdNot(String typeName, Long id);
}