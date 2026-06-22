package com.hotel.backend.service.Impl;

import com.hotel.backend.dto.request.FacilityRequest;
import com.hotel.backend.dto.response.FacilityResponse;
import com.hotel.backend.entity.Facility;
import com.hotel.backend.exception.DuplicateResourceException;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.repository.FacilityRepository;
import com.hotel.backend.service.FacilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    // ── READ ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<FacilityResponse> getAll() {
        log.debug("Lấy tất cả facilities");
        return facilityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FacilityResponse getById(Long id) {
        log.debug("Lấy facility id={}", id);
        return mapToResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacilityResponse> getByType(String type) {
        log.debug("Lọc facilities theo type={}", type);
        return facilityRepository.findByTypeIgnoreCaseOrderByFacilityNameAsc(type)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacilityResponse> search(String keyword) {
        log.debug("Tìm kiếm facilities keyword={}", keyword);
        return facilityRepository.findByFacilityNameContainingIgnoreCaseOrderByFacilityNameAsc(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ── WRITE ─────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public FacilityResponse create(FacilityRequest request) {
        log.info("Tạo facility: {}", request.getFacilityName());

        if (facilityRepository.existsByFacilityNameIgnoreCase(request.getFacilityName())) {
            throw new DuplicateResourceException("Facility", "facilityName", request.getFacilityName());
        }

        Facility facility = Facility.builder()
                .facilityName(request.getFacilityName())
                .type(request.getType())
                .description(request.getDescription())
                .icon(request.getIcon())
                .build();

        Facility saved = facilityRepository.save(facility);
        log.info("Đã tạo facility id={}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public FacilityResponse update(Long id, FacilityRequest request) {
        log.info("Cập nhật facility id={}", id);

        Facility facility = findOrThrow(id);

        if (facilityRepository.existsByFacilityNameIgnoreCaseAndIdNot(request.getFacilityName(), id)) {
            throw new DuplicateResourceException("Facility", "facilityName", request.getFacilityName());
        }

        facility.setFacilityName(request.getFacilityName());
        facility.setType(request.getType());
        facility.setDescription(request.getDescription());
        facility.setIcon(request.getIcon());

        Facility saved = facilityRepository.save(facility);
        log.info("Đã cập nhật facility id={}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Xóa facility id={}", id);
        Facility facility = findOrThrow(id);

        // Detach khỏi tất cả room types để tránh lỗi FK khi xóa
        facility.getRoomTypes().forEach(rt -> rt.getFacilities().remove(facility));

        facilityRepository.delete(facility);
        log.info("Đã xóa facility id={}", id);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private Facility findOrThrow(Long id) {
        return facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility", id));
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    private FacilityResponse mapToResponse(Facility entity) {
        return FacilityResponse.builder()
                .id(entity.getId())
                .facilityName(entity.getFacilityName())
                .type(entity.getType())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}