package com.hotel.backend.controller;

import com.hotel.backend.dto.request.RoomTypeRequest;
import com.hotel.backend.dto.response.ApiResponse;
import com.hotel.backend.dto.response.RoomTypeResponse;
import com.hotel.backend.service.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller cho RoomType.
 *
 * Base URL: /api/v1/room-types
 *
 * GET    /api/v1/room-types                          → getAll()
 * GET    /api/v1/room-types/{id}                     → getById()
 * GET    /api/v1/room-types?minPrice=&maxPrice=      → getByPriceRange()
 * POST   /api/v1/room-types                          → create()
 * PUT    /api/v1/room-types/{id}                     → update()
 * DELETE /api/v1/room-types/{id}                     → delete()
 */
@RestController
@RequestMapping("/api/v1/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomTypeResponse>>> getAll(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        List<RoomTypeResponse> data = (minPrice != null && maxPrice != null)
                ? roomTypeService.getByPriceRange(minPrice, maxPrice)
                : roomTypeService.getAll();

        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomTypeResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roomTypeService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoomTypeResponse>> create(
            @Valid @RequestBody RoomTypeRequest request) {

        RoomTypeResponse created = roomTypeService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo loại phòng thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomTypeResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody RoomTypeRequest request) {

        RoomTypeResponse updated = roomTypeService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật loại phòng thành công", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        roomTypeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa loại phòng thành công"));
    }
}