
package com.hotel.backend.controller;

import com.hotel.backend.dto.request.GalleryRequest;
import com.hotel.backend.dto.response.ApiResponse;
import com.hotel.backend.dto.response.GalleryResponse;
import com.hotel.backend.service.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST Controller cho Gallery.
 *
 * Base URL: /api/v1/galleries
 *
 * GET    /api/v1/galleries                           → getAll()
 * GET    /api/v1/galleries?type=INTERIOR             → getByType()
 * GET    /api/v1/galleries?keyword=pool              → search()
 * GET    /api/v1/galleries?roomId=5                  → getByRoomId()
 * GET    /api/v1/galleries?hotel=true                → getHotelGalleries() (room_id IS NULL)
 * GET    /api/v1/galleries/{id}                      → getById()
 * POST   /api/v1/galleries                           → create()
 * PUT    /api/v1/galleries/{id}                      → update()
 * DELETE /api/v1/galleries/{id}                      → delete()
 */
@RestController
@RequestMapping("/api/v1/galleries")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GalleryResponse>>> getAll(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {

        List<GalleryResponse> data;

        if (keyword != null && !keyword.isBlank()) {
            data = galleryService.search(keyword);
        } else if (type != null && !type.isBlank()) {
            data = galleryService.getByType(type);
        } else {
            data = galleryService.getAll();
        }

        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GalleryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(galleryService.getById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GalleryResponse>> create(
            @Valid @RequestBody GalleryRequest request) {

        GalleryResponse created = galleryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo ảnh thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GalleryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody GalleryRequest request) {

        GalleryResponse updated = galleryService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Cập nhật ảnh thành công", updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {

        galleryService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("Xóa ảnh thành công")
        );
    }
}