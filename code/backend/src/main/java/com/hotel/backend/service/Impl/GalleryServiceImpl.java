package com.hotel.backend.service.Impl;

import com.hotel.backend.dto.request.GalleryRequest;
import com.hotel.backend.dto.response.GalleryResponse;
import com.hotel.backend.entity.Gallery;
import com.hotel.backend.exception.DuplicateResourceException;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.repository.GalleryRepository;
import com.hotel.backend.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;

    // ==================== READ ====================

    @Override
    @Transactional(readOnly = true)
    public List<GalleryResponse> getAll() {
        log.debug("Lấy tất cả galleries");

        return galleryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GalleryResponse getById(Long id) {
        log.debug("Lấy gallery id={}", id);
        return mapToResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryResponse> getByType(String type) {
        log.debug("Lọc gallery theo type={}", type);

        return galleryRepository
                .findByTypeIgnoreCaseOrderByCreatedAtDesc(type)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryResponse> search(String keyword) {
        log.debug("Tìm kiếm gallery keyword={}", keyword);

        return galleryRepository
                .findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ==================== WRITE ====================

    @Override
    @Transactional
    public GalleryResponse create(GalleryRequest request) {

        log.info("Tạo gallery: {}", request.getTitle());

        if (galleryRepository.existsByImageUrl(request.getImageUrl())) {
            throw new DuplicateResourceException(
                    "Gallery",
                    "imageUrl",
                    request.getImageUrl()
            );
        }

        Gallery gallery = Gallery.builder()
                .title(request.getTitle())
                .type(request.getType())
                .imageUrl(request.getImageUrl())
                .build();

        Gallery saved = galleryRepository.save(gallery);

        log.info("Đã tạo gallery id={}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public GalleryResponse update(Long id, GalleryRequest request) {

        log.info("Cập nhật gallery id={}", id);

        Gallery gallery = findOrThrow(id);

        if (galleryRepository.existsByImageUrlAndIdNot(
                request.getImageUrl(), id)) {

            throw new DuplicateResourceException(
                    "Gallery",
                    "imageUrl",
                    request.getImageUrl()
            );
        }

        gallery.setTitle(request.getTitle());
        gallery.setType(request.getType());
        gallery.setImageUrl(request.getImageUrl());

        Gallery saved = galleryRepository.save(gallery);

        log.info("Đã cập nhật gallery id={}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        log.info("Xóa gallery id={}", id);

        Gallery gallery = findOrThrow(id);

        galleryRepository.delete(gallery);

        log.info("Đã xóa gallery id={}", id);
    }

    // ==================== HELPERS ====================

    private Gallery findOrThrow(Long id) {

        return galleryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Gallery", id));
    }

    private GalleryResponse mapToResponse(Gallery entity) {

        return GalleryResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .type(entity.getType())
                .imageUrl(entity.getImageUrl())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}