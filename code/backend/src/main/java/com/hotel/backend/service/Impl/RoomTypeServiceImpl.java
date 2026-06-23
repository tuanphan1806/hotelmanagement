package com.hotel.backend.service.Impl;

import com.hotel.backend.dto.request.RoomTypeRequest;
import com.hotel.backend.dto.response.FacilityResponse;
import com.hotel.backend.dto.response.RoomTypeResponse;
import com.hotel.backend.entity.Facility;
import com.hotel.backend.entity.RoomType;
import com.hotel.backend.exception.DuplicateResourceException;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.repository.FacilityRepository;
import com.hotel.backend.repository.RoomTypeRepository;
import com.hotel.backend.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final FacilityRepository facilityRepository;

    // ── READ ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<RoomTypeResponse> getAll() {
        log.debug("Lấy tất cả room types");
        return roomTypeRepository.findAllWithFacilities()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomTypeResponse getById(Long id) {
        log.debug("Lấy room type id={}", id);
        return mapToResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomTypeResponse> getByPriceRange(BigDecimal min, BigDecimal max) {
        log.debug("Lọc room type theo giá {} - {}", min, max);
        return roomTypeRepository.findByPriceBetweenOrderByPriceAsc(min, max)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── WRITE ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public RoomTypeResponse create(RoomTypeRequest request) {
        log.info("Tạo room type: {}", request.getTypeName());

        if (roomTypeRepository.existsByTypeNameIgnoreCase(request.getTypeName())) {
            throw new DuplicateResourceException("RoomType", "typeName", request.getTypeName());
        }

        RoomType roomType = RoomType.builder()
                .typeName(request.getTypeName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .build();

        resolveAndAssignFacilities(roomType, request.getFacilityIds());

        RoomType saved = roomTypeRepository.save(roomType);
        log.info("Đã tạo room type id={}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public RoomTypeResponse update(Long id, RoomTypeRequest request) {
        log.info("Cập nhật room type id={}", id);

        RoomType roomType = findOrThrow(id);

        if (roomTypeRepository.existsByTypeNameIgnoreCaseAndIdNot(request.getTypeName(), id)) {
            throw new DuplicateResourceException("RoomType", "typeName", request.getTypeName());
        }

        roomType.setTypeName(request.getTypeName());
        roomType.setDescription(request.getDescription());
        roomType.setPrice(request.getPrice());
        roomType.setImageUrl(request.getImageUrl());

        // Xóa toàn bộ facilities cũ, gán lại từ request
      roomType.getFacilities().clear();
        resolveAndAssignFacilities(roomType, request.getFacilityIds());

        RoomType saved = roomTypeRepository.save(roomType);
        log.info("Đã cập nhật room type id={}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Xóa room type id={}", id);
        RoomType roomType = findOrThrow(id);
     roomType.getFacilities().clear();  // dọn bảng room_type_facilities trước
        roomTypeRepository.delete(roomType);
        log.info("Đã xóa room type id={}", id);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private RoomType findOrThrow(Long id) {
        return roomTypeRepository.findByIdWithFacilities(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoomType", id));
    }

    /**
     * Resolve danh sách facilityIds → Facility entities rồi gán vào RoomType.
     * Ném lỗi nếu có bất kỳ ID nào không tồn tại trong DB.
     */
    private void resolveAndAssignFacilities(RoomType roomType, Set<Long> facilityIds) {
        if (facilityIds == null || facilityIds.isEmpty()) return;

        Set<Facility> found = facilityRepository.findAllByIdIn(facilityIds);

        if (found.size() != facilityIds.size()) {
            Set<Long> foundIds = found.stream().map(Facility::getId).collect(Collectors.toSet());
            Set<Long> missing  = facilityIds.stream()
                    .filter(fid -> !foundIds.contains(fid))
                    .collect(Collectors.toSet());
            throw new ResourceNotFoundException("Facility không tồn tại với ids: " + missing);
        }

      found.forEach(facility -> roomType.getFacilities().add(facility));
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    private RoomTypeResponse mapToResponse(RoomType entity) {
        List<FacilityResponse.Summary> facilitySummaries = entity.getFacilities()
                .stream()
                .map(f -> FacilityResponse.Summary.builder()
                        .id(f.getId())
                        .facilityName(f.getFacilityName())
                        .type(f.getType())
                        .build())
                .collect(Collectors.toList());

        return RoomTypeResponse.builder()
                .id(entity.getId())
                .typeName(entity.getTypeName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .facilities(facilitySummaries)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
