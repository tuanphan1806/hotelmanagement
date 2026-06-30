package com.hotel.backend.service.Impl;

import com.hotel.backend.constant.CleaningStatus;
import com.hotel.backend.constant.RoomStatus;
import com.hotel.backend.dto.request.RoomRequest;
import com.hotel.backend.dto.response.RoomPageResponse;
import com.hotel.backend.dto.response.RoomResponse;
import com.hotel.backend.entity.Room;
import com.hotel.backend.entity.RoomType;
import com.hotel.backend.exception.DuplicateResourceException;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.repository.RoomRepository;
import com.hotel.backend.repository.RoomTypeRepository;
import com.hotel.backend.service.RoomService;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.Sort;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j(topic = "ROOM-SERVICE")
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomResponse create(RoomRequest request) {
        log.info("Creating room with roomName={}", request.getRoomName());

        if (roomRepository.existsByRoomName(request.getRoomName())) {
            throw new DuplicateResourceException("Room", "roomName", request.getRoomName());
        }

        RoomType roomType = getRoomTypeById(request.getRoomTypeId());

        Room room = Room.builder()
                .roomName(request.getRoomName())
                .roomType(roomType)
                .floor(request.getFloor())
                .description(request.getDescription())
                .build();

        roomRepository.save(room);
        log.info("Room created successfully with roomId={}", room.getId());
        return RoomResponse.from(room);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomResponse update(Long id, RoomRequest request) {
        log.info("Updating roomId={}", id);

        Room room = getRoomById(id);

        if (!room.getRoomName().equals(request.getRoomName())
                && roomRepository.existsByRoomName(request.getRoomName())) {
            throw new DuplicateResourceException("Room", "roomName", request.getRoomName());
        }

        RoomType roomType = getRoomTypeById(request.getRoomTypeId());

        room.setRoomName(request.getRoomName());
        room.setRoomType(roomType);
        room.setFloor(request.getFloor());
        room.setDescription(request.getDescription());

        roomRepository.save(room);
        log.info("Update room successfully roomId={}", id);
        return RoomResponse.from(room);
    }

    @Override
    public RoomResponse getById(Long id) {
        log.info("Fetching roomId={}", id);
        return RoomResponse.from(getRoomById(id));
    }

    @Override
    public List<RoomResponse> getAll() {
        log.info("Fetching all rooms");
        List<RoomResponse> result = roomRepository.findAll()
                .stream()
                .map(RoomResponse::from)
                .toList();
        log.info("Found {} rooms", result.size());
        return result;
    }

    @Override
    public List<RoomResponse> search(String keyword, RoomStatus status, CleaningStatus cleaningStatus) {
        log.info("Searching rooms keyword={}, status={}, cleaningStatus={}", keyword, status, cleaningStatus);
        List<RoomResponse> result = roomRepository.search(keyword, status, cleaningStatus)
                .stream()
                .map(RoomResponse::from)
                .toList();
        log.info("Search found {} rooms", result.size());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("Deleting roomId={}", id);
        Room room = getRoomById(id);
        roomRepository.delete(room);
        log.info("Delete room successfully roomId={}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomResponse updateStatus(Long id, RoomStatus status) {
        log.info("Updating status roomId={}, status={}", id, status);
        Room room = getRoomById(id);
        room.setStatus(status);
        roomRepository.save(room);
        log.info("Update status successfully roomId={}", id);
        return RoomResponse.from(room);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomResponse updateCleaningStatus(Long id, CleaningStatus cleaningStatus) {
        log.info("Updating cleaning status roomId={}, cleaningStatus={}", id, cleaningStatus);
        Room room = getRoomById(id);
        room.setCleaningStatus(cleaningStatus);
        roomRepository.save(room);
        log.info("Update cleaning status successfully roomId={}", id);
        return RoomResponse.from(room);
    }

    @Override
    public RoomPageResponse findAll(String keyword, String sort, int page, int size) {
        log.info("Fetching rooms keyword={}, sort={}, page={}, size={}", keyword, sort, page, size);

        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("^(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                order = matcher.group(3).equalsIgnoreCase("asc")
                        ? new Sort.Order(Sort.Direction.ASC, columnName)
                        : new Sort.Order(Sort.Direction.DESC, columnName);
            }
        }

        int pageNo = page > 0 ? page - 1 : 0;
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(order));

        Page<Room> entityPage = StringUtils.hasLength(keyword)
                ? roomRepository.searchByKeyword("%" + keyword.toLowerCase() + "%", pageable)
                : roomRepository.findAll(pageable);

        log.info("Found {} rooms", entityPage.getTotalElements());
        return getRoomPageResponse(pageNo, size, entityPage);
    }

    private static RoomPageResponse getRoomPageResponse(int page, int size, Page<Room> rooms) {
        List<RoomResponse> roomList = rooms.stream()
                .map(RoomResponse::from)
                .toList();

        RoomPageResponse response = new RoomPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElements(rooms.getTotalElements());
        response.setTotalPages(rooms.getTotalPages());
        response.setRooms(roomList);
        return response;
    }



    // ---- private helpers ----
    private Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    private RoomType getRoomTypeById(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room type not found"));
    }
}