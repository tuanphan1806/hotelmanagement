package com.hotel.backend.repository;

import com.hotel.backend.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    /**
     * Lọc theo loại ảnh.
     */
    List<Gallery> findByTypeIgnoreCaseOrderByCreatedAtDesc(String type);

    /**
     * Tìm kiếm theo tiêu đề.
     */
    List<Gallery> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);

    /**
     * Kiểm tra URL ảnh đã tồn tại chưa.
     */
    boolean existsByImageUrl(String imageUrl);

    /**
     * Kiểm tra URL ảnh đã tồn tại ở bản ghi khác.
     */
    boolean existsByImageUrlAndIdNot(String imageUrl, Long id);
}